# ReconToolkit

Java library for clients and services implementing the [reconciliation API](https://reconciliation-api.github.io/specs/).

This is a work in progress, which should ideally evolve into the following:
* Java classes to represent the objects involved in the protocol (entities, properties, types, reconciliation candidates…) with easy JSON (de)serialization
* A client to query reconciliation services from a Java application, which transparently supports all versions of the protocol (0.1, 0.2 and the upcoming one)
* Helpful utilities to implement a reconciliation service in a Java application (perhaps even with some coupling to some established web framework?)
* Sensible modularity, so that one can decide to depend on only what is needed.

The goal would be that OpenRefine uses this library to query reconciliation services.

Contributions very welcome!
