const Joi = require('joi')
const BaseModel = require('../utils/base-model.js')

module.exports = new BaseModel('Share', {
  UserName: Joi.string().required(),
  data: Joi.string().required(),
  latitude: Joi.number().required(),
  longitude: Joi.number().required(),
  imageId: Joi.array().required(),
  description: Joi.string(),
})
