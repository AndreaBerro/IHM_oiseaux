const Joi = require('joi')
const BaseModel = require('../utils/base-model.js')

module.exports = new BaseModel('Notification', {
  title: Joi.string().required(),
  description: Joi.string().required(),
  type: Joi.number().required(),
  date: Joi.string().required(),
  time: Joi.string().required()
})