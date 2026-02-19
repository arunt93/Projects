# GraphQL Pagination Queries

## 1. Cursor-Based Pagination

### First Page
```bash
curl -X POST \
  http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -d '{
    "query": "query { 
      products(first: 5) { 
        edges { 
          cursor 
          node { 
            id 
            name 
            price 
            stock 
          } 
        } 
        pageInfo { 
          hasNextPage 
          hasPreviousPage 
          startCursor 
          endCursor 
        } 
        totalCount 
      } 
    }"
  }'
```

### Next Page (replace CURSOR_HERE with actual cursor)
```bash
curl -X POST \
  http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -d '{
    "query": "query { 
      products(first: 5, after: \"CURSOR_HERE\") { 
        edges { 
          cursor 
          node { 
            id 
            name 
            price 
            stock 
          } 
        } 
        pageInfo { 
          hasNextPage 
          hasPreviousPage 
          startCursor 
          endCursor 
        } 
        totalCount 
      } 
    }"
  }'
```

## 2. Offset-Based Pagination

### First Page
```bash
curl -X POST \
  http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -d '{
    "query": "query { 
      productsPaginated(offset: 0, limit: 5) { 
        edges { 
          node { 
            id 
            name 
            price 
            stock 
          } 
        } 
        pageInfo { 
          hasNextPage 
          hasPreviousPage 
        } 
        totalCount 
      } 
    }"
  }'
```

### Second Page
```bash
curl -X POST \
  http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -d '{
    "query": "query { 
      productsPaginated(offset: 5, limit: 5) { 
        edges { 
          node { 
            id 
            name 
            price 
            stock 
          } 
        } 
        pageInfo { 
          hasNextPage 
          hasPreviousPage 
        } 
        totalCount 
      } 
    }"
  }'
```

## 3. Test with Sample Data Creation

### Create Sample Products
```bash
curl -X POST \
  http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -d '{
    "query": "mutation { 
      createProduct(input: { 
        name: \"Laptop\", 
        description: \"Gaming laptop\", 
        price: 1200.00, 
        stock: 50 
      }) { 
        id 
        name 
        price 
      } 
    }"
  }'
```

### Create Multiple Products (run multiple times)
```bash
curl -X POST \
  http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -d '{
    "query": "mutation { 
      createProduct(input: { 
        name: \"Mouse\", 
        description: \"Wireless mouse\", 
        price: 25.00, 
        stock: 100 
      }) { 
        id 
        name 
        price 
      } 
    }"
  }'
```

## 4. GraphQL Playground Alternative

You can also test these queries in:
- GraphQL Playground: http://localhost:8080/playground
- GraphiQL: http://localhost:8080/graphiql
- Postman (GraphQL request type)

Just paste the query part without the curl wrapper:

```graphql
query {
  products(first: 5) {
    edges {
      cursor
      node {
        id
        name
        price
        stock
      }
    }
    pageInfo {
      hasNextPage
      hasPreviousPage
      startCursor
      endCursor
    }
    totalCount
  }
}
```
