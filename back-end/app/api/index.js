const { Router } = require('express')

const ShareRouter = require('./share')
const TokenRouter = require('./token')
const NotificationRouter = require('./notifications')

const router = new Router()
router.get('/status', (req, res) => res.status(200).json('ok'))
router.use('/share', ShareRouter)
router.use('/token', TokenRouter)
router.use('/notifications', NotificationRouter)

module.exports = router
