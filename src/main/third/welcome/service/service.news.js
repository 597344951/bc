const crypto = require('crypto')
const fs = require('fs')

const location = require('../config').location
const db = require('../lib/db')

function News(template) {
  this._template = template
}
News.prototype.create = async function() {
  let sql = `
    CREATE TABLE IF NOT EXISTS news (
      id INT(11) NOT NULL AUTO_INCREMENT,
      update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
      add_date TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
      title VARCHAR(128) NOT NULL COMMENT '标题',
      content TEXT NOT NULL COMMENT '内容',
      PRIMARY KEY (id)
    )  ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='新闻'
  `
  await this._template.execute(sql)
}
News.prototype.add = async function(news) {
  let sql = 'insert into news (title, content, add_date) values (?, ?, ?)'
  await this._template.execute(sql, [news.title, news.content, new Date()])
}
News.prototype.query = async function(page, size) {
  let countSql = 'select count(1) as count from news'
  let count = await this._template.execute(countSql)
  let sql = 'select * from news order by update_date desc limit ?, ?'
  let result = await this._template.execute(sql, [(page - 1) * size, size])
  return {rows: result.results, total: count.results[0].count}
}
News.prototype.addImage = async function(image) {
  let type = image.name.split('.').pop()
  let buffer = fs.readFileSync(image.path)
  let md5 = crypto.createHash('md5')
  md5.update(buffer)
  let md5Hex = md5.digest('hex')
  let name = `${md5Hex}.${type}`
  fs.writeFileSync(`${location.image}/${name}`, buffer)
  return `${location.relative.image}/${name}`
}
News.prototype.complete = async function() {
  await this._template.release()
}

module.exports.getInstance = async () => {
  return new News(await db.getTemplate())
}
