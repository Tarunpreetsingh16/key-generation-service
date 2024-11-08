### Key Generation Service
## Description
Key Generation Service is the core of URL shortener feature. This service provides the key that will be appended to the base URL, which will then be shared with the client as part of the shortened URL by the [URL shortener service](https://github.com/Tarunpreetsingh16/url_shortener). 

## Why making this a separate service?
For our simple project, it makes sense to make it a part of URL shortener service, but in the real world the traffic can be in millions/billions. Read traffic will be higher than write, hence we should leave URL shortener service alone to deal with redirection of the URLs. This is the reason I am choosing to keep key generation service as a separate microservice. Also key generation need to keep up with the traffic so it can generate new keys on the side without causing delay in the main service.

## Components in this service
![component diagram](https://github.com/Tarunpreetsingh16/key-generation-service/blob/develop/documentation/images/component_diagram.jpeg)
