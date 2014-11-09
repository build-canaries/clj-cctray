# cctray-clj

A Clojure library designed to parse the cctray format into a user friendly clojure map.

## Map keys

- `:name`
  The name of the project exactly as it appeared in the xml file.

- `:project-name`
  The normalised name of the project.

- `:stage`
  For ThoughtWorks Go parsed projects this will be the normalised stage name.

- `:job`
  For ThoughtWorks Go parsed projects this will be the normalised job name.

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

## Normalised strings

Normalised strings are lower case and "sentanceized" which means camel, snake and kebab cased words are converted to normal sentances with spaces.

```
CamelCased_Snake-Kebab => camel cased snake kebab
```

## CC Tray XML Spec

See https://github.com/robertmaldon/cc_dashboard/blob/master/README.md#summary

## License

Copyright © 2014 Christopher Martin and friends

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
