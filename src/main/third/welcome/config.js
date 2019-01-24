module.exports = {
  __home: __dirname,
  server: {
    port: 3000,
    name: 'Welcome to 0_0 .'
  },
  log4js: {
    appenders: {
      console: { type: 'console' },
      stdout: { type: 'stdout' },
      dateFile: {
        type: 'dateFile',
        filename: __dirname + '/logs/the-all.log',
        pattern: '.yyyy-MM-dd'
      }
    },
    categories: {
      default: { appenders: ['console', 'dateFile'], level: 'debug' }
    }
  },
  mysql: {
    host: '192.168.0.8',
    port: 3306,
    user: 'root',
    password: '123456',
    connectionLimit: 2,
    queueLimit: 5,
    database: 'gz'
  },
  location: {
    image: `${__dirname}/public/images`,
    relative: {
      image: '/images'
    }
  }
}
