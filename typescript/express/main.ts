import express, { Request, Response } from "express";
import { SugaClient } from "@/suga/client";
import bodyParser from "body-parser";

const app = express();
const suga = new SugaClient();

app.use(bodyParser.raw({ type: "*/*" }));

app.get("/read/:name", async (req: Request, res: Response) => {
  try {
    const name = req.params.name;
    const contents = await suga.image.read(name);
    res.type("text/plain").send(contents.toString("utf-8"));
  } catch (e: any) {
    res.status(500).json({ detail: e.message || String(e) });
  }
});

app.post("/write/:name", async (req: Request, res: Response) => {
  try {
    const name = req.params.name;
    const body = req.body;
    await suga.image.write(name, body);
    res.type("text/plain").send(`File '${name}' written to bucket.`);
  } catch (e: any) {
    res.status(500).json({ detail: e.message || String(e) });
  }
});

const port = process.env.PORT || 3000;
app.listen(port, () => {
  console.log(`Server running on port ${port}`);
});
