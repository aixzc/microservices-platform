<template>
  <el-form size="large" :model="state.ruleForm" :rules="state.rules" class="login-content-form" ref="loginRef">
    <el-form-item class="login-animation1" prop="username">
      <el-input text :placeholder="$t('message.account.accountPlaceholder1')" v-model="state.ruleForm.username"
                clearable autocomplete="off">
        <template #prefix>
          <el-icon class="el-input__icon">
            <ele-User/>
          </el-icon>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item class="login-animation2" prop="password">
      <el-input
          :type="state.isShowPassword ? 'text' : 'password'"
          :placeholder="$t('message.account.accountPlaceholder2')"
          v-model="state.ruleForm.password"
          autocomplete="off"
      >
        <template #prefix>
          <el-icon class="el-input__icon">
            <ele-Unlock/>
          </el-icon>
        </template>
        <template #suffix>
          <i
              class="iconfont el-input__icon login-content-password"
              :class="state.isShowPassword ? 'icon-yincangmima' : 'icon-xianshimima'"
              @click="state.isShowPassword = !state.isShowPassword"
          >
          </i>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item class="login-animation3" prop="clientId">
      <el-select
          :placeholder="$t('message.account.accountPlaceholder4')"
          v-model="state.ruleForm.clientId"
          autocomplete="off"
      >
        <template #prefix>
          <el-icon class="el-input__icon">
            <ele-Briefcase/>
          </el-icon>
        </template>
        <el-option v-for="system in state.clients" :key="system.clientId" :label="system.clientName"
                   :value="system.clientId"/>
      </el-select>
    </el-form-item>
    <el-form-item class="login-animation4" prop="validCode">
      <el-col :span="15">
        <el-input
            text
            maxlength="4"
            :placeholder="$t('message.account.accountPlaceholder3')"
            v-model="state.ruleForm.validCode"
            clearable
            autocomplete="off"
        >
          <template #prefix>
            <el-icon class="el-input__icon">
              <ele-Position/>
            </el-icon>
          </template>
        </el-input>
      </el-col>
      <el-col :span="1"></el-col>
      <el-col :span="8">
        <img class="login-content-code" :src="state.checkCodeUrl" @click="genCode"/>
      </el-col>
    </el-form-item>
    <el-form-item class="login-animation4">
      <el-button type="primary" class="login-content-submit" round v-waves @click="onSignIn(loginRef)"
                 :loading="state.loading.signIn">
        <span>{{ $t('message.account.accountBtnText') }}</span>
      </el-button>
    </el-form-item>
  </el-form>
</template>

<script setup lang="ts" name="loginAccount">
import {computed, onMounted, reactive, ref} from 'vue';
import {useRoute, useRouter} from 'vue-router';
import type {FormInstance} from 'element-plus';
import {useI18n} from 'vue-i18n';
import {storeToRefs} from 'pinia';
import {useThemeConfig} from '/@/stores/themeConfig';
import {formatAxis} from '/@/utils/formatTime';
import {useClientApi} from '/@/api/system/client';
import {v4 as uuidv4} from 'uuid';
import model from '/@/api/common/model';
import {signIn} from '/@/views/login/ts/account';
import {NextLoading} from "/@/utils/loading";
import {ElMessage} from 'element-plus';

// 定义变量内容
const {t} = useI18n();
const storesThemeConfig = useThemeConfig();
const {themeConfig} = storeToRefs(storesThemeConfig);
const route = useRoute();
const router = useRouter();
const baseIp = import.meta.env.VITE_API_URL;
const clientApi = useClientApi();
const loginRef = ref<FormInstance>();
const state = reactive({
  isShowPassword: false,
  checkCodeUrl: '',
  ruleForm: {
    username: '',
    password: '123456',
    validCode: '',
    grant_type: 'password_code',
    deviceId: '',
    clientId: ''
  },
  rules: {
    username: {required: true, message: '用户名不可为空', trigger: 'blur'},
    password: {required: true, message: '密码不可为空', trigger: 'blur'},
    validCode: {required: true, message: '请输入验证码', trigger: 'blur'},
    clientId: {required: true, message: '请选择需要登陆的系统', trigger: 'change'},
  },
  clients: [],
  loading: {
    signIn: false,
  },
});

// 时间获取
const currentTime = computed(() => {
  return formatAxis(new Date());
});
// 登录
const onSignIn = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate(async (valid: boolean) => {
    if (valid) {
      state.loading.signIn = true;
      const clientMap = state.clients.reduce((map, obj) => {
        map.set(obj.clientId, {clientId: obj.clientId, clientSecret: obj.clientSecretStr});
        return map;
      }, new Map<string, object>());
      const isNoPower = await signIn(state.ruleForm, clientMap.get(state.ruleForm.clientId)).finally(() => {
        state.loading.signIn = false;
        genCode();
      });
      signInSuccess(isNoPower);
    } else {
      return false;
    }
  });
};
// 登录成功后的跳转ElMessage
const signInSuccess = (isNoPower: boolean | undefined) => {
  if (!isNoPower || isNoPower == undefined) {
    ElMessage.warning('抱歉，您没有登录权限');
    loginRef.value.resetFields();
  } else {
    // 初始化登录成功时间问候语
    let currentTimeInfo = currentTime.value;
    // 登录成功，跳到转首页
    // 如果是复制粘贴的路径，非首页/登录页，那么登录成功后重定向到对应的路径中
    if (route.query?.redirect) {
      router.push({
        path: <string>route.query?.redirect,
        query: Object.keys(<string>route.query?.params).length > 0 ? JSON.parse(<string>route.query?.params) : '',
      });
    } else {
      router.push('/');
    }
    // 登录成功提示
    const signInText = t('message.signInText');
    ElMessage.success(`${currentTimeInfo}，${signInText}`);
    // 添加 loading，防止第一次进入界面时出现短暂空白
    NextLoading.start();
  }
  state.loading.signIn = false;
};
const getAllClient = async () => {
  state.clients = await clientApi.getAll();
}

//生成验证码
const genCode = () => {
  const uuid = uuidv4();
  state.checkCodeUrl = baseIp + model.uaa.name + `validata/code/${uuid}`;
  state.ruleForm.deviceId = uuid;
}

onMounted(() => {
  genCode();
  getAllClient();
});


</script>

<style scoped lang="scss">
.login-content-form {
  margin-top: 20px;
  @for $i from 1 through 4 {
    .login-animation#{$i} {
      opacity: 0;
      animation-name: error-num;
      animation-duration: 0.5s;
      animation-fill-mode: forwards;
      animation-delay: calc($i/10) + s;
    }
  }

  .login-content-password {
    display: inline-block;
    width: 20px;
    cursor: pointer;

    &:hover {
      color: #909399;
    }
  }

  .login-content-code {
    width: 100%;
    padding: 0;
    font-weight: bold;
    letter-spacing: 5px;
  }

  .login-content-submit {
    width: 100%;
    letter-spacing: 2px;
    font-weight: 300;
    margin-top: 15px;
  }
}
</style>
