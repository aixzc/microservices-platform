
//模块维护
export default {
    'user': {
        name: 'api-user/'
    },
    'uaa': {
        name: 'api-uaa/'
    },
    'log': {
        name: 'api-log/'
    },
    'search': {
        name: 'api-search/'
    },
    'generator': {
        name: 'api-generator/'
    },
    'file': {
        name: 'api-file/'
    },
    '/api-uaa/': {
        target: 'http://127.0.0.1:9900',
        changeOrigin: true,
    }
};