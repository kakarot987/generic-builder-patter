# Pizza Builder — type-specific fluent Builder Pattern

This project demonstrates the **Builder pattern** by constructing an immutable
`Pizza` from required values (`size`, `cheese`) and optional fluent choices
(`crust`, `sauce`, `toppings`, `extraCheese`, `wellDone`).

Despite the current repository name `generic-builder-patter`, this is a
**type-specific Pizza builder**, not a generic Java `Builder<T>` utility.
A future repository rename such as `pizza-builder-pattern` would be clearer;
renaming is intentionally not part of this fix.

## Run

Requires Java 17 and Maven.

```bash
mvn spring-boot:run
mvn test
```

Try the API:

```bash
curl -X POST http://localhost:8080/api/pizza/build \
  -H 'Content-Type: application/json' \
  -d '{"size":"Medium","cheese":"Mozzarella"}'
```

Omitted `crust` and `sauce` now preserve `Classic` and `Tomato`.
Explicit blank values fail validation. Unknown presets and invalid build
requests return HTTP **400**.

## Why a Builder?

Required parameters go in the Builder constructor and optional properties are
added via fluent methods. The final immutable `Pizza` is created only in
`build()`. For a simple object with few fields, a Java record or constructor
may be clearer; use a Builder when optional combinations become hard to read.

![Demo screenshot](img.png)
