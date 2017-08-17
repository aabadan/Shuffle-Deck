# Shuffle Deck
<br />
A RESTful microservice that implements a card shuffling algorithm.Gradle used for the build, and Jetty to host. <br />
For deploy-time dependency injection of a shuffling algorithm, Jetty web xml configuration used.  <br />
<br />
Specifications:<br />
·         A microservice that stores and shuffles card decks.<br />
·         A card may be represented as a simple string such as “5-heart”, or “K-spade”.<br />
·         A deck is an ordered list of 52 standard playing cards.<br />
·   A RESTful interface that allows a user to:<br />
·         PUT an idempotent request for the creation of a new named deck.  New decks are created in some initial sorted order.<br />
·         POST a request to shuffle an existing named deck.<br />
·         GET a list of the current decks persisted in the service.<br />
·         GET a named deck in its current sorted/shuffled order.<br />
·         DELETE a named deck..<br />
·         Persist the decks in-memory only, but stub the persistence layer such that it can be later upgraded to a durable datastore.<br />
·         Implement a simple shuffling algorithm that simply randomizes the deck in-place.<br />
·         Implement a more complex algorithm that simulates hand-shuffling, i.e. splitting the deck in half and interleaving the two halves, repeating the process multiple times.<br />
·         Allow switching the algorithms at deploy-time only via configuration.<br />
