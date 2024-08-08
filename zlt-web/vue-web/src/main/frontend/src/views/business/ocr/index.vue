<template>
  <div class="table-demo-container layout-padding">
    <div class="table-demo-padding layout-padding-view layout-padding-auto">

      <TableSearch :search="state.tableData.search" @search="onSearch"/>
      <el-upload :action="baseIp + model.file.name + `files-anon`"
                 multiple :limit="1" :on-success="uploadOcrImg"
                 :headers="{Authorization: `Bearer ${Session.get('token')}`}"
                 :show-file-list="false">
        <el-button size="default" type="success" class="ml10">
          <el-icon>
            <ele-FolderAdd/>
          </el-icon>
          识别文件
        </el-button>
      </el-upload>
      <el-button size="default" type="success" class="ml10" @click="readFile">
        <el-icon>
          <ele-FolderAdd/>
        </el-icon>
        识别
      </el-button>
      <Table
          ref="tableRef"
          v-bind="state.tableData"
          class="table-demo"
          @delRow="onTableDelRow"
          @pageChange="onTablePageChange"
          @sortHeader="onSortHeader"
      />
    </div>
  </div>
</template>

<script setup lang="ts" name="ocr">

import {defineAsyncComponent, reactive, ref, onMounted} from 'vue';
import {ElMessage, UploadProps} from 'element-plus';
import {useOcrApi} from '/@/api/business/ocr';
import model from "/@/api/common/model";
import {Session} from "/@/utils/storage";
import {useFileApi} from "/@/api/system/file";

// 引入组件
const Table = defineAsyncComponent(() => import('/@/components/table/index.vue'));
const TableSearch = defineAsyncComponent(() => import('/@/views/make/tableDemo/search.vue'));

const baseIp = import.meta.env.VITE_API_URL;
// 定义变量内容
const tableRef = ref<RefType>();
const state = reactive<OcrTableState>({
  tableData: {
    // 列表数据（必传）
    data: [],
    // 表头内容（必传，注意格式）
    header: [
      {key: 'name', colWidth: '', title: '识别文件', type: 'text', isCheck: true},
      {key: 'name', colWidth: '', title: '日期', type: 'text', isCheck: true}
    ],
    // 配置项（必传）
    config: {
      total: 0, // 列表总数
      loading: true, // loading 加载
      isBorder: false, // 是否显示表格边框
      isSerialNo: true, // 是否显示表格序号
      isSelection: true, // 是否显示表格多选
      isOperate: true, // 是否显示表格操作栏
    },
    // 搜索表单，动态生成（传空数组时，将不显示搜索，注意格式）
    search: [],
    // 搜索参数（不用传，用于分页、搜索时传给后台的值，`getTableData` 中使用）
    param: {
      page: 1,
      limit: 10,
    }
  },
});

// 初始化列表数据
const getTableData = async () => {
  state.tableData.config.loading = true;
  const res = await useOcrApi().getList(state.tableData.param);
  state.tableData.data = res.data;
  // 数据总数（模拟，真实从接口取）
  state.tableData.config.total = res.total;
  setTimeout(() => {
    state.tableData.config.loading = false;
  }, 500);
};
// 搜索点击时表单回调
const onSearch = (data: EmptyObjectType) => {
  state.tableData.param = Object.assign({}, state.tableData.param, {...data});
  tableRef.value.pageReset();
};
// 删除当前项回调
const onTableDelRow = (row: EmptyObjectType) => {
  ElMessage.success(`删除${row.name}成功！`);
  getTableData();
};
// 分页改变时回调
const onTablePageChange = (page: TableDemoPageType) => {
  state.tableData.param.pageNum = page.pageNum;
  state.tableData.param.pageSize = page.pageSize;
  getTableData();
};

const uploadOcrImg: UploadProps['onSuccess'] = async (response) => {
  await useFileApi().getUrl({path: response.path}).then(async (res) => {
    await useOcrApi().ocrAnalysis(response.id);
  });
}
// 拖动显示列排序回调
const onSortHeader = (data: TableHeaderType[]) => {
  state.tableData.header = data;
};

const readFile = async () => {
  // const res = await useFileApi().getUrl({path: 'zlt/6cd3bc05-7678-4c7f-84b2-88d129789922/绝句.jpeg'});
  // await useOcrApi().ocrAnalysis({imgUrl: res});
}

// 页面加载时
onMounted(() => {
  getTableData();
});
</script>

<style scoped lang="scss">
.table-demo-container {
  .table-demo-padding {
    padding: 15px;

    .table-demo {
      flex: 1;
      overflow: hidden;
    }
  }
}
</style>