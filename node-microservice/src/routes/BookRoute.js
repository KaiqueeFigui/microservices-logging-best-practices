import { Router } from "express";

const bookRoutes = new Router()

bookRoutes.get("/:id", (req, res) => {
    res.send(`Getting book with id ${req.params.id}`)
})

export const bookRoute = bookRoutes