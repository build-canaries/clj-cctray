# clj-cctray

A Clojure library designed to parse the cctray format into a user friendly clojure map.

## Usage

```clojure
(ns your-app.core
  (:require [clj-cctray.core :as :parser]))

(parser/get-projects "some-url")
(parser/get-projects "some-url" :options [:some-option])
```

## Options

Options allow you to modify the output based on modifiers included in the project.

- `:go`
  If the cctray is coming from a Go CI server you can provide this option to add additional Go specific parsing, such as
  extracting stages and jobs.

- `:snap`
  If the cctray is coming from a Snap CI server you can provide this option to add additional Snap specific parsing, such as
  extracting stages and jobs.

- `:normalise`
  This will cause the :name, :stage and :job (if they exist) to be normalised (see below for more details about normalisation)

- `:normalise-name`
  This will cause the :name to be normalised (see below for more details about normalisation)

- `:normalise-stage`
  This will cause the :stage (if it exists) to be normalised (see below for more details about normalisation)

- `:normalise-job`
  This will cause the :job (if it exists) to be normalised (see below for more details about normalisation)

## Map keys

- `:name`
  The name of the project.

- `:activity`
  The current state of the project as a keyword. Either `:sleeping`, `:building` or `:checking-modifications`.

- `:last-build-status`
  A brief description of the last build as a keyword. Either `:success`, `:failure`, `:exception` or `:unknown`.

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

## Installation

`clj-cctray` is available as a Maven artifact from [Clojars](http://clojars.org/clj-cctray)

## CC Tray XML Spec

See https://github.com/robertmaldon/cc_dashboard/blob/master/README.md#summary

## License

Copyright © 2014 Build Canaries and friends

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
