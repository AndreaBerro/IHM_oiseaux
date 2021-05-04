const { Router } = require('express')
const fs = require('fs')
const formidable = require('formidable')
const image = require('imageinfo') // 引用imageinfo模块
// const path = require('path')

const { Share } = require('../../../models')
const manageAllErrors = require('../../../utils/routes/error-management')

const router = new Router()

// // 可以使用图片名中详细时间作为id， 前提： 图片名前JPEG去掉
// router.get('/', (req, res) => {
//   try {
//     function readFileList(floderPath, filesList) {
//       const files = fs.readdirSync(floderPath)
//       files.forEach((itm, index) => {
//         const stat = fs.statSync(floderPath + itm)
//         if (stat.isDirectory()) {
//           // 递归读取文件
//           readFileList(`${floderPath + itm}/`, filesList)
//         } else {
//           const obj = {}// 定义一个对象存放文件的路径和名字
//           obj.path = floderPath// 路径
//           obj.filename = itm// 名字
//           filesList.push(obj)
//         }
//       })
//     }

//     const getFiles = {
//       // 获取文件夹下的所有文件
//       getFileList(floderPath) {
//         const filesList = []
//         readFileList(floderPath, filesList)
//         return filesList
//       },
//       // 获取文件夹下的所有图片
//       getImageFiles(floderPath) {
//         const imageList = []

//         this.getFileList(floderPath).forEach((item) => {
//           console.log(item)
//           const ms = image(fs.readFileSync(item.path + item.filename))
//           console.log(ms)
//           ms.mimeType && (imageList.push(item.filename))
//         })
//         return imageList
//       },
//     }


//     console.log(req.query.ids)
//     // const fileName = req.params.imageId
//     getFiles.getImageFiles('D:\\Courses\\Semestre_6\\IHM\\backend\\back-end\\app\\api\\share\\upload')
//     res.status(200).json(Share.get())
//   } catch (err) {
//     manageAllErrors(res, err)
//   }
// })
//
// router.post('/', (req, res) => {
//   try {
//     const share = Share.create({ ...req.body })
//     res.status(201).json(share)
//   } catch (err) {
//     manageAllErrors(res, err)
//   }
// })

module.exports = router
