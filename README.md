# Playing with scala - AKKA

Source code for upcoming blog post

```sh
-> curl localhost:9000/decks/request -F 'file=@/Users/me/Code/blog-projects/playing-with-scala-akka/decks/challenger/2020/allied-fires.csv'
{"id":"a56624f0-2641-4e7d-acb3-80cc0d937fab","deckId":"9f30a86e-cdd7-40d0-9f97-f81efefe02bd","status":{"value":"pending"}}

-> curl localhost:9000/decks/request/a56624f0-2641-4e7d-acb3-80cc0d937fab
{"id":"a56624f0-2641-4e7d-acb3-80cc0d937fab","deckId":"9f30a86e-cdd7-40d0-9f97-f81efefe02bd","status":{"value":"success","deck_id":"9f30a86e-cdd7-40d0-9f97-f81efefe02bd"}}

-> curl localhost:9000/decks/9f30a86e-cdd7-40d0-9f97-f81efefe02bd
{"id":"9f30a86e-cdd7-40d0-9f97-f81efefe02bd","cards":[...]}
```
