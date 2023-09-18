import express from 'express'
import {v4} from 'uuid'
import { bookRoute } from './routes/BookRoute.js'

const app = express()
const port = 3000


app.use((req, res, next) => {
  let correlationId = req.get("correlationId")

  if (!correlationId) {
    correlationId = v4()
  }
  console.log(correlationId)
  next()
})

app.use("/books", bookRoute)

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})