const { Router } = require('express')

const ShareRouter = require('./share')

const router = new Router()
router.get('/status', (req, res) => res.status(200).json('ok'))
router.use('/share', ShareRouter)

module.exports = router
