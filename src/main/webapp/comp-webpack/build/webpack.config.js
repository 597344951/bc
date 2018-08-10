/**完整打包容器**/
const path = require('path')
const VueLoaderPlugin = require('vue-loader/lib/plugin')
const CleanWebpackPlugin = require('clean-webpack-plugin')

module.exports = {
  mode: 'production', //生产模式
  //mode: 'development', //开发模式
  entry: '../index-all.js',
  //devtool: 'eval-source-map',
  output: {
    path: path.resolve(__dirname, '../../components'),
    filename: 'main.js'
  },
  resolve: {
    alias: {
      vue$: 'vue/dist/vue.esm.js'
    }
  },
  module: {
    rules: [{
        test: /\.(js)$/,
        use: ['babel-loader'],
        exclude: /node_modules/
      },
      {
        test: /\.vue$/,
        loader: 'vue-loader',
        exclude: /node_modules/
      },
      // 它会应用到普通的 `.css` 文件
      // 以及 `.vue` 文件中的 `<style>` 块
      {
        test: /\.css$/,
        use: ['vue-style-loader', 'css-loader']
      },
      { //处理 html 中通过 img 引入的图片，background-image 设置的图片不可以
        test: /\.html$/,
        use: "html-loader"
      }, { //处理图片，会在 output 目录中生成图片文件，js 中需要使用 require("*.jpg")先行引入才可以，同样 html 中通过 background-image 设置的图片不可以，但 css 中通过 background-image 设置的图片可以
        test: /\.(jpg|png)$/,
        use: {
          loader: "file-loader",
          options: {
            outputPath: "../../images/", //这里的 images 貌似没什么作用，但不写不行，可以是任意的一个或多个字符
            name: "[name].[hash:8].[ext]", //8表示截取 hash 的长度
            useRelativePath: true //这个必须与 outputPath 结合使用才可以处理 css 中的引入的图片
          }
        }
      }
    ]
  },
  plugins: [
    new VueLoaderPlugin(),
    new CleanWebpackPlugin(['dist'], {
      root: `${__dirname}/../`,
      verbose: true,
      dry: false
    })
  ]
}