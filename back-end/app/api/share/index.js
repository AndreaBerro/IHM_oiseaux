const { Router } = require('express')
const fs = require('fs')
const formidable = require('formidable')
const path = require('path')
const admin = require('firebase-admin')

const { Share } = require('../../models')
const { Token } = require('../../models')
const manageAllErrors = require('../../utils/routes/error-management')


const router = new Router()

// $env:GOOGLE_APPLICATION_CREDENTIALS="D:\Courses\Semestre_6\IHM\backend\oiseaux-d2da4-firebase-adminsdk-x60hr-fcfbf5e58b.json"
// export GOOGLE_APPLICATION_CREDENTIALS="D:\Courses\Semestre_6\IHM\backend\oiseaux-d2da4-firebase-adminsdk-x60hr-fcfbf5e58b.json"

admin.initializeApp({
  credential: admin.credential.applicationDefault(),
  // databaseURL: 'https://<DATABASE_NAME>.firebaseio.com'
})

router.get('/', (req, res) => {
  try {
    console.log('hello again in get')
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
    console.log(err)
    manageAllErrors(res, err)
  }
  console.log('Will sent message')
  const registrationTokens = []
  const tokens = Token.get()

  tokens.forEach((t) => {
    console.log(t.Token)
    registrationTokens.push(t.Token)
  })
  // registrationTokens.push(tokens[0].Token)

  console.log('RegistraToken ----->', registrationTokens, Share.items[Share.items.length - 1].description)

  const message = {
    data: {
      score: '850',
      time: '2:45',
    },
    notification: {
      title: 'New Share !',
      body: Share.items[Share.items.length - 1].description,
    },
    tokens: registrationTokens,
  }

  // Send a message to the device corresponding to the provided
  // registration token.
  admin.messaging().sendMulticast(message)
    .then((response) => {
      console.log(`${response.successCount} messages were sent successfully`)
      if (response.failureCount > 0) {
        const failedTokens = []
        response.responses.forEach((resp, idx) => {
          if (!resp.success) {
            failedTokens.push(registrationTokens[idx])
          }
        })
        console.log(`List of tokens that caused failures: ${failedTokens}`)
      }
    })
})

router.post('/image', (req, res, next) => {
  const form = new formidable.IncomingForm()
  const targetFile = path.join(__dirname, './upload')
  form.uploadDir = targetFile
  try {
    form.parse(req, (err, fields, files) => {
      console.log('Parsing done.')
      // console.log(files)
      // console.log(files.userName)
      const oldpath = files.file.path
      const newpath = path.join(path.dirname(oldpath), files.file.name)
      fs.rename(oldpath, newpath, (error) => {
        console.log(`Image Path${newpath}`)
        res.end('Image upload success!')
      })
    })
    res.status(201).json(res)
  } catch (err) {
    // manageAllErrors(res, err) // 有未知错误。。。
  }
})


module.exports = router
