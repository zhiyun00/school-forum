module.exports = {
  publicPath: './',
}

module.exports = {
  devServer: {
    // host: "",  //指定要使用的 host
    port: 8081,   //指定端口号以侦听
    // hotOnly: false, //启用热模块替换，而无需页面刷新作为构建失败时的回退。
  },
  chainWebpack: config => {
    config.plugin('html').tap(args => {
        args[0].title= 'WHU-admin'
        return args
      })
    },
};