const manageAllErrors = (res, err) => {
  if (err.name === 'NotFoundError') {
    console.log(404)
    res.status(404).end()
  } else if (err.name === 'ValidationError') {
    console.log(400)
    res.status(400).json(err.extra)
  } else {
    console.log(500)
    res.status(500).json(err)
  }
}

module.exports = manageAllErrors
