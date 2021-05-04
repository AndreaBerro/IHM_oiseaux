const { Router } = require('express')
const { Token } = require('../../models')
const manageAllErrors = require('../../utils/routes/error-management')

const router = new Router()

router.post('/', (req, res) => {
  try {
    console.log(req.body.Token)
    const tokens = Token.get()
    tokens.forEach(t => {
      console.log(t.Token)
      // eslint-disable-next-line eqeqeq
      if (t.Token === req.body.Token) {
        throw new Error('Token Exist')
      }
    })
    const token = Token.create({ ...req.body })
    res.status(201).json(token)
  } catch (err) {
    manageAllErrors(res, err)
  }
})


module.exports = router
