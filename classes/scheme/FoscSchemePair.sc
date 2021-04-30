/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscSchemePair


SUMMARY:: Returns a FoscSchemePair.


DESCRIPTION:: Scheme pair

Initialize Scheme pairs with a tuple, two separate values or another Scheme pair.


USAGE::

'''
code::
a = FoscSchemePair(-1, 1);
a.format;
'''
'''
code::
a = FoscSchemePair('spacing', 4);
a.format;
'''
'''
code::
a = FoscSchemePair('font-name', "Times");
a.format;
'''
'''
code::
a = FoscSchemePair(-1, 1);
b = FoscSchemePair(a);
b.format;
'''
'''
code::
a = FoscSchemePair([-1, 1]);
a.format;
'''
------------------------------------------------------------------------------------------------------------ */
FoscSchemePair : FoscScheme {
    *new { |... args|
        case
        { args.size == 1 && { args[0].isKindOf(FoscSchemePair) } } {
            args = args[0].value;
        }
        { args.size == 1 && { args[0].isKindOf(SequenceableCollection) } } {
            args = args[0][0..1];
        }
        { args.size == 2 } {
            args = args;
        }
        { args.size == 0 } {
            args = [0, 1];
        }
        { Error("Can not initialize % from: %.".format(this.name, args)).throw };

        ^super.new(args);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • prGetFormattedValue
    -------------------------------------------------------------------------------------------------------- */
    prGetFormattedValue {
        var lhs, rhs;
        lhs = FoscScheme.formatSchemeValue(value[0]);
        rhs = FoscScheme.formatSchemeValue(value[1], forceQuotes: true);
        ^"(% . %)".format(lhs, rhs);
    }
    /* --------------------------------------------------------------------------------------------------------
    • prGetLilypondFormat
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormat {
        ^"#'%".format(this.prGetFormattedValue);
    }
}
