/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscPostscript


SUMMARY:: Returns a FoscPostscript.


DESCRIPTION:: TODO


USAGE::

'''

• FoscPostscript

Postscript commands: http://www.math.ubc.ca/~cass/courses/ps.html

• Example 1

code::
p = FoscPostscript();
p.newpath();
p.moveto(0, 0);
p.rlineto(0, -10);
p.rlineto(10, 0);
p.rlineto(0, 10);
p.rlineto(-10, 0);
p.closepath();
p.gsave();
p.setrgbcolor(0.5, 1, 0.5);
p.fill();
p.grestore();
p.setrgbcolor(1, 0, 0);
p.setlinewidth(1);
p.stroke();
p.format;

code::
p.show;

img:: ![](../img/markup-postscript-1.png)
'''

p = "%/fosc/docs/img/markup-postscript-1".format(Platform.userExtensionDir);
p.writePNG("%.ly".format(p));



'''
------------------------------------------------------------------------------------------------------------ */ 
FoscPostscript : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <operators;
    *new { |operators|
    	^super.new.init(operators);
    }
    init { |argOperators|
        // • TODO: check that all argOperators are of FoscPostscriptOperator type
        operators = argOperators;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC METHODS: SPECIAL METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • ++ (abjad: __add__)
    
    code::
	p = FoscPostscript();
    p.newpath();
    p.moveto(0, 0);
    p.rlineto(0, -10);
    p.stroke();

    code::
    q = FoscPostscript();
    q.newpath();
    q.moveto(0, 10);
    q.rlineto(10, -20);
    q.stroke();

    code::
    (p ++ q).show;

    img:: ![](../img/markup-postscript-2.png)
    '''

    p = "%/fosc/docs/img/markup-postscript-2".format(Platform.userExtensionDir);
    (p ++ q).writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    ++ { |postscript|
    	var newOperators;
    	if (postscript.isKindOf(FoscPostscript).not) {
    		^error("FoscPostscript::++: argument must be a FoscPostscript.");
    	};
    	newOperators = this.operators ++ postscript.operators;
    	^this.species.new(newOperators);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • illustrate


    code::
    p = FoscPostscript();
    p.moveto(1.0, 1.0);
    p.setlinewidth(2.5);
    p.lineto(3, -4);
    p.stroke;

    code::
    f = p.illustrate;   
    f.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    illustrate {
        var markup;
        markup = FoscMarkup.postscript(this);
        ^markup.illustrate;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • format

    code::
    p = FoscPostscript();
    p.moveto(1.0, 1.0);
    p.setlinewidth(2.5);
    p.lineto(3, -4);
    p.stroke;

    code::
    p.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    format {
    	^this.str;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • show

    code::
    p = FoscPostscript();
    p.moveto(1.0, 1.0);
    p.setlinewidth(2.5);
    p.lineto(3, -4);
    p.stroke;

    code::
    p.show;

    img:: ![](../img/markup-postscript-3.png)
    '''

    p = "%/fosc/docs/img/markup-postscript-3".format(Platform.userExtensionDir);
    p.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • str

    Gets string representation of Postscript.
    
    Return string.
    
    code::
    p = FoscPostscript();
    p.moveto(1.0, 1.0);
    p.setlinewidth(2.5);
    p.lineto(3, -4);
    p.stroke;

    code::
    p.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    str {
        var result;
        if (operators.isEmpty) { ^"" };
        result = "";
        operators.do { |each| result = result ++ "\n%".format(each.format) };
        ^result;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prFormatArgument
    '''
    -------------------------------------------------------------------------------------------------------- */
    *prFormatArgument { |argument|
    	var contents;
    	case
    	{ argument.isKindOf(String) } {
    		if (argument[0] == $/) { ^argument };
    		^"(%)".format(argument);
    	}
    	{ argument.isKindOf(SequenceableCollection) } {
    		if (argument.isEmpty) { ^"[ ]" };
    		contents = "";
    		argument.do { |each| contents = contents + FoscPostscript.prFormatArgument(each) };
    		^"[ % ]".format(contents[1..]);
    	}
    	{ argument.isKindOf(Boolean) } {
    		^argument.asString;
    	}
    	{ argument.isKindOf(Number) } {
    		//!!! argument = mathtools.integer_equivalent_number_to_integer(argument)
    		^argument.asString;
    	};
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prWithOperator

    code::
    p = FoscPostscript();
    p.newpath();
    p.moveto(0, 0);
    p.rlineto(0, -10);
    p.stroke

    code::
    p.operators.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''
    '''
    -------------------------------------------------------------------------------------------------------- */
    prWithOperator { |operator|
        if (operators.isNil) { operators = [] };
    	operators = operators.add(operator);
  		^this.species.new(operators);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • asMarkup
    '''
    -------------------------------------------------------------------------------------------------------- */
    asMarkup {
    	^FoscMarkup.postscript(this);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • arc (not in abjad)

    code::
    p = FoscPostscript();
    p.moveto(30, -20);
    p.arc(20, -20, 10, 0, 360);
    p.stroke;
    
    code::
    p.show;

    img:: ![](../img/markup-postscript-4.png)
    '''

    p = "%/fosc/docs/img/markup-postscript-4".format(Platform.userExtensionDir);
    p.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    arc { |x, y, radius, startAngle, endAngle|
        ^this.prWithOperator(FoscPostscriptOperator('arc', x, y, radius, startAngle, endAngle));
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • charpath
    '''
    -------------------------------------------------------------------------------------------------------- */
    charpath { |text, modifyFont=true|
    	text = text.asString;
    	^this.prWithOperator(FoscPostscriptOperator('charpath', text, modifyFont));
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • closepath
    
    code::
	p = FoscPostscript();
    p.closepath;
    p.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    closepath {
    	^this.prWithOperator(FoscPostscriptOperator('closepath'));
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • curveto
    
    code::
	p = FoscPostscript();
    p.curveto(10, -10, 20, -30, 30, 0);
    p.stroke;
    
    code::
    p.show;

    img:: ![](../img/markup-postscript-5.png)
    '''

    p = "%/fosc/docs/img/markup-postscript-5".format(Platform.userExtensionDir);
    p.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    curveto { |x1, y1, x2, y2, x3, y3|
    	// • TODO: do args need to be converted to floats ?
    	^this.prWithOperator(FoscPostscriptOperator('curveto', x1, y1, x2, y2, x3, y3));
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • fill

    code::
    p = FoscPostscript();
    p.fill;
    p.format;        
    '''
    -------------------------------------------------------------------------------------------------------- */
    fill {
    	^this.prWithOperator(FoscPostscriptOperator('fill'));
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • findfont

    code::
    p = FoscPostscript();
    p.findfont('Times Roman');
    p.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    findfont { |fontName|
    	fontName = fontName.asString;
    	fontName = fontName.replace(" ", "-");
    	fontName = "/%".format(fontName);
    	^this.prWithOperator(FoscPostscriptOperator('findfont', fontName));
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • grestore

    code::
	p = FoscPostscript();
    p.grestore;
    p.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    grestore {
    	^this.prWithOperator(FoscPostscriptOperator('grestore'));
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • gsave
    
    code::
	p = FoscPostscript();
    p.gsave;
    p.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    gsave {
    	^this.prWithOperator(FoscPostscriptOperator('gsave'));
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • lineto

    code::
	p = FoscPostscript();
    p.lineto(3.0, -4.0);
    p.stroke;

    code::
    p.show;

    img:: ![](../img/markup-postscript-6.png)
    '''

    p = "%/fosc/docs/img/markup-postscript-6".format(Platform.userExtensionDir);
    p.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    lineto { |x, y|
    	// • TODO: convert x and y to float ?
    	^this.prWithOperator(FoscPostscriptOperator('lineto', x, y));
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • moveto
    
    code::
	p = FoscPostscript();
    p.moveto(3.0, -4.0);
    p.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    moveto { |x, y|
    	// • TODO: convert x and y to float ?
    	^this.prWithOperator(FoscPostscriptOperator('moveto', x, y));
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • newpath

    code::
	p = FoscPostscript();
    p.newpath;
    p.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    newpath {
    	^this.prWithOperator(FoscPostscriptOperator('newpath'));
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • rcurveto
    
    code::
	p = FoscPostscript();
    p.rcurveto(0, 1, 1.5, 2, 3, 6);
    p.stroke;

    code::
    p.show;

    img:: ![](../img/markup-postscript-7.png)
    '''

    p = "%/fosc/docs/img/markup-postscript-7".format(Platform.userExtensionDir);
    p.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    rcurveto { |dx1, dy1, dx2, dy2, dx3, dy3|
    	// • TODO: convert dx1, dy1, dx2, dy2, dx3, dy3 to float ?
    	^this.prWithOperator(FoscPostscriptOperator('rcurveto', dx1, dy1, dx2, dy2, dx3, dy3));
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • rlineto
    
    code::
	p = FoscPostscript();
    p.rlineto(1.2, 2.0);
    p.stroke;

    code::
    p.show;

    img:: ![](../img/markup-postscript-8.png)
    '''

    p = "%/fosc/docs/img/markup-postscript-8".format(Platform.userExtensionDir);
    p.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    rlineto { |dx, dy|
    	// • TODO: convert dx, dy to float ?
    	^this.prWithOperator(FoscPostscriptOperator('rlineto', dx, dy));
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • rmoveto

    code::
    p = FoscPostscript();
    p.rmoveto(1.2, 2.0);
    p.stroke;

    code::
    p.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    rmoveto { |dx, dy|
        // • TODO: convert dx, dy to float ?
        ^this.prWithOperator(FoscPostscriptOperator('rmoveto', dx, dy));
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • rotate
    
    code::
	p = FoscPostscript();
    p.rotate(45);
    p.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    rotate { |degrees|
    	// • TODO: convert degrees to float ?
    	^this.prWithOperator(FoscPostscriptOperator('rotate', degrees));
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • scale
    
    code::
	p = FoscPostscript();
    p.scale(2, 1);
    p.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    scale { |dx, dy|
    	// • TODO: convert dx, dy to float ?
    	^this.prWithOperator(FoscPostscriptOperator('scale', dx, dy));
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • scalefont
    
    code::
	p = FoscPostscript();
    p.scalefont(12);
    p.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    scalefont { |fontSize|
    	// • TODO: convert fontSize to float ?
    	^this.prWithOperator(FoscPostscriptOperator('scalefont', fontSize));
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • setdash

    code::
    p = FoscPostscript();
    p.setdash([2, 1], 3);
    p.format;

    code::
    p = FoscPostscript();
    p.setdash([2, 1]);
    p.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    setdash { |array, offset=0|
    	array = array ? [];
    	// • TODO: convert array, offset to float ?
        ^this.prWithOperator(FoscPostscriptOperator('setdash', array, offset));
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • setfont

    code::
	p = FoscPostscript();
    p.setfont;
    p.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    setfont {
    	^this.prWithOperator(FoscPostscriptOperator('setfont'));
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • setgray

    code::
    p = FoscPostscript();
    p.setgray(0.75);
    p.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    setgray { |grayValue|
    	^this.prWithOperator(FoscPostscriptOperator('setgray', grayValue));
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • setlinewidth

    code::
    p = FoscPostscript();
    p.setlinewidth(12.2);
    p.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    setlinewidth { |width|
    	//!! width = float(width)
    	^this.prWithOperator(FoscPostscriptOperator('setlinewidth', width));
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • setrgbcolor

    code::
    p = FoscPostscript();
    p.setrgbcolor(0.75, 1, 0.2);
    p.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    setrgbcolor { |red, green, blue|
    	^this.prWithOperator(FoscPostscriptOperator('setrgbcolor', red, green, blue));
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • show
    
    • TODO: solve naming conflict with FoscObject::show

    code::
    p = FoscPostscript();
    p.show("This is text.");
    p.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    show { |text|
    	text = text.asString;
    	^this.prWithOperator(FoscPostscriptOperator('show', text));
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • stroke
    
    code::
	p = FoscPostscript();
    p.stroke;
    p.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    stroke {
    	^this.prWithOperator(FoscPostscriptOperator('stroke'));
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • translate
    
    code::
	p = FoscPostscript();
    p.translate(100, 300);
    p.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    translate { |dx, dy|
    	// • TODO: convert dx, dy to float ??
    	^this.prWithOperator(FoscPostscriptOperator('translate', dx, dy));
    }
}
