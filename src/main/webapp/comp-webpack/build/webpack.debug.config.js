'use strict'
const webpack = require('webpack')
const merge = require('webpack-merge')
const baseWebpackConfig = require('./webpack.base.conf')

const webpackConfig = merge(baseWebpackConfig, {
	mode: 'development', 
})
module.exports = webpackConfig
