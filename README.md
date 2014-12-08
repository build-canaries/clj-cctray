# clj-cctray [![Build Status](https://snap-ci.com/build-canaries/clj-cctray/branch/master/build_image)](https://snap-ci.com/build-canaries/clj-cctray/branch/master)

A Clojure library designed to parse the cctray format into a user friendly clojure map.

## Usage

```clojure
(ns your-app.core
  (:require [clj-cctray.core :as :parser]))

(parser/get-projects "some-url")
(parser/get-projects "some-url" {:some-option "the-value"})
```

## Options

Options are a map of keywords with values that can be passed to modify the returned project map list in some way or set global library settings.

Most options will require the value to be in a specific format and checks are not performed, this is your responsibility. Failure to set the value correctly will most likely result in an exception being thrown.

- `:server`
  A keyword representing the CI server the xml is coming from to allow any server specific parsing. Currently the only values that trigger specific parsing are `:go` and `:snap` any other values will result in this option being ignored.

- `:normalise`
  This will cause various map keys (if they exist) to be normalised (see below for more details about normalisation). Can take the values `:all` (to noramlise all the following keys), `:name`, `:stage` or `:job` (to just normalise that key).

- `:strict-certificate-checks`
  By default SSL certificates are not checked (more info below), setting this option to `true` will cause this to happen.

- `:http-timeout-seconds`
  Setting this option to any valid `integer` will set the timeout of http connections in seconds. The default timeout is 30 seconds.

## Map keys

- `:name`
  The name of the project.

- `:activity`
  The current state of the project as a keyword. Either `:sleeping`, `:building` or `:checking-modifications`.

- `:last-build-status`
  A brief description of the last build as a keyword. Either `:success`, `:failure`, `:error`, `:exception` or `:unknown`.

- `:last-build-label`
  The build label exactly as it appeared in the xml file.

- `:last-build-time`
  When the last build occurred as a JodaTime `DateTime` object.

- `next-build-time`
  When the next build is scheduled to occur (or when the next check to see whether a build should be performed is
  scheduled to occur) as a JodaTime `DateTime` object.

- `:web-url`
  A URL for where more detail can be found about this project exactly as it appeared in the xml file.

- `:prognosis`
  The derived health of the project based on the activity and last build status as a keyword. Either `:sick-building`,
  `:sick`, `:healthy`, `:healthy-building` or `:unknown`

If `:go` or `:snap` are used as `:options` then the following keys will also be added.

- `:stage`
  The projects stage name.

- `:job`
  For projects job name.

## Normalised strings

Normalised strings are lower case and "sentenceized" which means camel, snake and kebab cased words are converted to normal sentences with spaces.

```
CamelCased_Snake-Kebab => camel cased snake kebab
```

## Local files and remote files can be read

By default, you can load local files on your disk and remote files over http or https. If you are using `clj-cctray` on a webserver then we recommend you lock down the options to only allow reading over http or https.

## Installation

`clj-cctray` is available as a Maven artifact from [Clojars](http://clojars.org/clj-cctray)

## CC Tray XML Spec

See https://github.com/robertmaldon/cc_dashboard/blob/master/README.md#summary

## License

Copyright © 2014 Build Canaries and friends

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
