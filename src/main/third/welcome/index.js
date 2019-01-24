const Koa = require('koa')
const body = require('koa-body')
const Router = require('koa-router')

const app = new Koa()
const router = new Router()

const config = require('./config')
const logger = require('./lib/log')('MAIN')

const News = require('./service/service.news')
const db = require('./lib/db')

app.use(
  require('koa-static')(`${config.__home}/public`, {
    maxage: 1000 * 60 * 10 //浏览器缓存时间（毫秒）
  })
)

app.use(
  body({
    jsonLimit: '5mb', // 控制body的parse转换大小 default 1mb
    formLimit: '4096kb', //  控制你post的大小  default 56kb
    multipart: true,
    formidable: {
      maxFileSize: 50 * 1024 * 1024 // 设置上传文件大小最大限制, 默认2M
    }
  })
)

router.post('/image', async (ctx, next) => {
  let news = await News.getInstance()
  let rpath = await news.addImage(ctx.request.files.image)
  await news.complete()
  ctx.body = { state: 'SUCCESS', path: rpath }
})
router.post('/news', async (ctx, next) => {
  let news = await News.getInstance()
  await news.add(ctx.request.body)
  await news.complete()
  ctx.body = { state: 'SUCCESS' }
})

router.get('/news', async (ctx, next) => {
  let page = ctx.query.page ? ctx.query.page : 1
  let size = ctx.query.size ? ctx.query.size : 20
  let news = await News.getInstance()
  let newses = await news.query(Number.parseInt(page), Number.parseInt(size))
  await news.complete()
  ctx.body = { state: 'SUCCESS', newses: newses }
})

router.get('/query', async (ctx, next) => {
  let page = ctx.query.page ? Number.parseInt(ctx.query.page) : 1
  let size = ctx.query.size ? Number.parseInt(ctx.query.size) : 20
  let table = ctx.query.table
  let template = await db.getTemplate()
  let result = await template.execute(`select * from ${table} limit ?, ?`, [
    (page - 1) * size,
    size
  ])
  await template.release()
  ctx.body = result.results
})
router.post('/query', async (ctx, next) => {
  let sql = ctx.request.body.sql
  let params = ctx.request.body.params
  let template = await db.getTemplate()
  let result = await template.execute(sql, params)
  await template.release()
  ctx.body = result.results
})
app.use(router.routes()).use(router.allowedMethods())
app.use(async ctx => {
  ctx.throw(404, `No page found: ${ctx.url}`)
})

app.listen(config.server.port)
logger.info(`Server start at ${config.server.port} ...`)
