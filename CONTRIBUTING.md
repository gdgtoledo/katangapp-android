# CONTRIBUTING

## Chaining
Avoid chaining. Use variables instead of allowing chaining. Please justify in your commit comment
why you need to chain method calls.

## Indent
The unit to define an indent will be four spaces. Use a unit after each inner block.

## Lines

### Length
Restrict line length to 100 characters. If you need more space, cut the line for the first
parameter. If you still need more space, cut by parameter in a method call.

If you are working with strings, do not cut words.

### Blank lines
Adding blank lines in between code adds clarity. Let's use the criteria that if two lines can be
interchangeable, then can be joined. Otherwise, let's separate with a blank line.

Let's use one blank line between methods, one blank line after class declaration, and one blank line
before class-closing curly brackets.

Add a blank line before a line if it adds clarity when separating this line from the previous one,
which was indented.

#### File endings
Do not add a blank line after the last line with content.

#### Empty methods
Do not add a blank line in an empty method.

#### Method endings
There won't be a blank line before method endings.

## Names
Use camel case for variables and methods.

## Order
Having an order criterium  will increase speed when searching for them.

### Attributes in a class
Use alphabetical order to define the attributes in a class.

### Attributes in a XML element
Use common Android-style order (see [http://blog.jetbrains.com/idea/2013/10/rearrange-attributes-in-android-xml-files-with-intellij-idea-13](http://blog.jetbrains.com/idea/2013/10/rearrange-attributes-in-android-xml-files-with-intellij-idea-13)).

### Imports
Order imports by this criterium:

First the application packages, then order them alphabetically, adding blank lines when needed.

Static imports should go first.

### Methods in a class
Use alphabetical order to define the methods in a class.

### Modifiers (Attributes and Methods)
On first place, add the public elements, then, separated by a blank line, the protected ones, and
finally the private ones.

On each type, first add the constants (static final), then the static, and finally the regular ones.

## Organize imports
Separate imports with a blank line on those ones differing their second package names.