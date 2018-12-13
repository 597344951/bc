const path = require('path')
const VueLoaderPlugin = require('vue-loader/lib/plugin')
const CleanWebpackPlugin = require('clean-webpack-plugin')

module.exports = {
  mode: 'production', //生产模式
  //mode: 'development', //开发模式
  //devtool: 'eval-source-map', //打包源码-便于调试
  entry: './src/components/index.js',
  output: {
    path: path.resolve(__dirname, '../dist'),
    filename: 'xx-components.js'
  },
  module: {
    rules: [
      {
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
        use: ['vue-style-loader', 'css-loader', 'less-loader']
      },
      {
        test: /\.less/,
        use: [
          'vue-style-loader',
          'css-loader',
          'less-loader'
        ]
      },
      {
        test: /\.(png|jpg|gif|woff|woff2|ttf|eot|svg)$/,
        loader: 'url-loader',
        query: {
          limit: 8192
        }

      }
    ]
  },
  plugins: [
    new VueLoaderPlugin(),
    new CleanWebpackPlugin(['dist'], {
      root: `${__dirname}/../`,
      exclude: ['lib'],
      verbose: true,
      dry: false
    })
  ]
}
