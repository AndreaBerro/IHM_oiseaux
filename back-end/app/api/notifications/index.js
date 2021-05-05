const { Router } = require('express')
const path = require('path')
const { Notification } = require('../../models')
const manageAllErrors = require('../../utils/routes/error-management')


const router = new Router()

router.get('/', (req, res) => {
  try {
    res.status(200).json(Notification.get())
  } catch (err) {
    manageAllErrors(res, err)
  }
})

router.post('/', (req, res) => {
  try {
    console.log(req.body)
    const notification = Notification.create({ ...req.body })
    res.status(201).json(notification)
  } catch (err) {
    manageAllErrors(res, err)
  }
})


module.exports = router