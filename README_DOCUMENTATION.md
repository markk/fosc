# Fosc Documentation

## Requirements

- [GNU Make](https://www.gnu.org/software/make/) (optional)
- [Pandoc](https://pandoc.org/) (for HTML output)

## Building

Documentation is extracted from the Fosc class files and written to Markdown
format. This can then be converted to HTML using Pandoc.

### Single class file

Markdown extraction can be done in SuperCollider for a single class as follows:

```supercollider
FoscMarkdownFile("/path/to/ClassFile.sc").write("/path/to/MarkdownFile.md");
```

Code blocks found in the class file are interpreted and their post output and
any images generated are included in the output. See the `TestClass.sc` file for
details.

### Class tree

```supercollider
FoscDocumentationGenerator("/path/to/class/tree", "/path/to/markdown/tree", clean: false).generate;
```

Set `clean` to `true` to delete the output path first.

### Using GNU Make

It may be convenient to use GNU Make to build the documentation, particularly
during development. A `Makefile` is provided with the following defined targets:

doc
: make the Markdown format documentation for the entire class tree

html
: make the HTML format documentation using Pandoc for the entire class tree

clean
: clean output directories

Individual target outputs can also be built, e.g.:

```sh
make www/base/FoscObject.html
```

### Using Pandoc

Pandoc converts the generated Markdown files to HTML, adding syntax highlighting
for SuperCollider and LilyPond code blocks. The SuperCollider syntax is defined
in `supercollider.xml`; LilyPond syntax highlighting is built-in to Pandoc.

## BUGS

### Unresolved build failures

Some code examples in particular class files regularly but inconsistently fail
to build - the interpreter crashes without displaying any error output or
writing the Markdown file.

- `agent/FoscMutation.sc`, particularly the `rewriteMeter` method
- `maker/FoscDurationSpecifier.sc`

Multiple examples in these classes are flagged `nointerpret` until this can be
fixed.

### Interactive code examples

Some methods designed for use in an interactive environment use `Post` directly
to display formatted information. I don't know how to capture this using
`interpret`. One example of this is `FoscMeter.inspect`.
