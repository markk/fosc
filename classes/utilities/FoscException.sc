/* ------------------------------------------------------------------------------------------------------------
TITLE:: FoscException


SUMMARY:: Returns a FoscException.


DESCRIPTION::

• FoscMethodError

Normally used in conjunction with Object:assert.

USAGE::

'''
Throw an assertion error:

code::nointerpret
FoscSegmentMaker(meterSpecifier: 2);

post::
ERROR: Class not defined.
'''
------------------------------------------------------------------------------------------------------------ */
FoscMethodError : MethodError {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <selector, <message;
    *new { |method, message|
        ^super.new(nil, method.ownerClass).init(method.name, message);
    }
    init { |argSelector, argMessage|
        selector = argSelector;
        message = argMessage;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • errorString
    -------------------------------------------------------------------------------------------------------- */
    errorString {
       ^"ERROR: %:%: %".format(receiver, selector, message);
    }
    /* --------------------------------------------------------------------------------------------------------
    • reportError
    -------------------------------------------------------------------------------------------------------- */
    reportError {
        this.errorString.postln;
    }
}
/* ------------------------------------------------------------------------------------------------------------
• FoscValueError

Normally used in conjunction with Object:assert.


Throw an assertion error:

code::nointerpret
FoscSegmentMaker(meterSpecifier: 2);
'''
------------------------------------------------------------------------------------------------------------ */
FoscValueError : MethodError {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <selector, <argName, <value;
    *new { |method, argName, value|
        ^super.new(nil, method.ownerClass).init(method.name, argName, value);
    }
    init { |argSelector, argArgName, argValue|
        selector = argSelector;
        argName = argArgName;
        value = argValue;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • errorString
    -------------------------------------------------------------------------------------------------------- */
    errorString {
       ^"ERROR: %:%: bad value for '%': %.".format(receiver, selector, argName, value.cs);
    }
    /* --------------------------------------------------------------------------------------------------------
    • reportError
    -------------------------------------------------------------------------------------------------------- */
    reportError {
        this.errorString.postln;
    }
}
