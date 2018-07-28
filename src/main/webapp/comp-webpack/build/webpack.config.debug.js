/**debug 开发模式**/
const path = require('path')
const VueLoaderPlugin = require('vue-loader/lib/plugin')
const CleanWebpackPlugin = require('clean-webpack-plugin')

module.exports = {
  //mode: 'production', //生产模式
  mode: 'development', //开发模式
  entry: '../index-all.js',
  devtool: 'eval-source-map',
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
        test: /\.(html)$/,
        use: {
          loader: 'html-loader',
          options: {
            attrs: [':data-src']
          }
        }
      }, {
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