.PHONY: build bootstrap
build: server

bootstrap:
	sn-vcpkg install --manifest vcpkg.json
server: main.scala vcpkg.json
	sn-vcpkg scala-cli --manifest vcpkg.json -- --power package -o $@ main.scala
