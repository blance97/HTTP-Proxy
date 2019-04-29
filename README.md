# HTTP-Proxy
HTTP Proxy(CSSE 432)

This project used two data classes for reuse. This helped separated what changed and what stayed the same. The main logic code is in ProxyThread.java. It has 5 methods(not including run and constructor) to abstract certain parts and make is easier to read and think about. 

##Caching
For caching I used a data class model for each page and stored the url,header, and html body. Then I used a queue to keep track of which sites were visited recently. However, I am only checking on url. So if the same person requests a cached page with different headers it will not update the headers. So if that happens anre you want to update headers just change clients. Caching is stored for each client.