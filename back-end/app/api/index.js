const { Router } = require('express')

const ShareRouter = require('./share')
const TokenRouter = require('./token')

const router = new Router()
router.get('/status', (req, res) => res.status(200).json('ok'))
router.use('/share', ShareRouter)
router.use('/token', TokenRouter)

module.exports = router
