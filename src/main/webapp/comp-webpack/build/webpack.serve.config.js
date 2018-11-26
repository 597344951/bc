'use strict'
const webpack = require('webpack')
const merge = require('webpack-merge')
const path = require('path')
const baseWebpackConfig = require('./webpack.base.conf')

const webpackConfig = merge(baseWebpackConfig, {
	mode: 'development',
	devServer: {
		// 设定根目录为${webapp}
		contentBase: path.join(__dirname, '../../'),
		compress: true,
		port: 9000
  }
})
module.exports = webpackConfig
