# PhonePe

![Transaction list](http://i.imgur.com/0TtH3Q4.png)

##### Work done:
* Transaction list shows Pending and Completed transactions.

* Transactions of both types are fetched from the server.

* Pagination works

* Project follows the MVP design pattern.

* Transactions are cached on the device, but are never updated even if there's a newer version available on the server. Solving this would require maintaining timestamps in `Transaction` model to accurately identify updated transactions.

* Amounts shown in the transaction list items are pretty formatted according to the Indian format. For example, 135600 will show up as 1,35,600

##### What I had aimed for, but could not finish:
* Auto-load more items when the list is scrolled to bottom. I wanted to create this manually instead of relying on a library. But it's also pretty easy to do so. Simply register a scroll listener and auto-load items when the end is reached.

* Unit tests. However, since MVP was followed, doing automated unit would have been pretty easy.

* Transaction details

* All transactions with a specific user

> The last two were out of the scope for this assignment, but I still wanted to do them because I quite enjoyed imagining how the app > will work.

##### What could be improved:
* Showing of progress indicator. This is shown only when past transactions are being downloaded, but when pending transactions are downloaded.

##### Gotchas:
* 5 items are fetched from the server every time the "Load more transactions" button is pressed.

* There's a delay of 1s for every network request. I added it because otherwise the loading indicator hides too quickly (Your server is too fast).
