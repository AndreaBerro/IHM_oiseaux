const Joi = require('joi')
const BaseModel = require('../utils/base-model.js')

module.exports = new BaseModel('Token', {
  Token: Joi.string().required(),
})
