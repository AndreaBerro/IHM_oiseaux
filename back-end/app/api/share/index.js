const { Router } = require('express')
const fs = require('fs')
const formidable = require('formidable')
const path = require('path')
const ImageRouter = require('./image')
const { Share } = require('../../models')
const manageAllErrors = require('../../utils/routes/error-management')


const router = new Router()

router.get('/', (req, res) => {
  try {
    res.status(200).json(Share.get())
  } catch (err) {
    manageAllErrors(res, err)
  }
})

router.post('/', (req, res) => {
  try {
    console.log(req.body)
    const share = Share.create({ ...req.body })
    res.status(201).json(share)
  } catch (err) {
    manageAllErrors(res, err)
  }
})

router.post('/image', (req, res, next) => {
  const form = new formidable.IncomingForm()
  const targetFile = path.join(__dirname, './upload')
  form.uploadDir = targetFile
  try {
    form.parse(req, function (err, fields, files) {
      console.log('Parsing done.')
      // console.log(files)
      // console.log(files.userName)
      const oldpath = files.file.path
      const newpath = path.join(path.dirname(oldpath), files.file.name)
      fs.rename(oldpath, newpath, (error) => {
        console.log("Image Path" + newpath)
        res.end('Image upload success!')
      })
    })
    res.status(201).json(res)
  } catch (err) {
    // manageAllErrors(res, err) // 有未知错误。。。
  }
})

router.use('/image', ImageRouter)

module.exports = router
