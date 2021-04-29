/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscLilypondSettingNameManager


SUMMARY:: Returns a FoscLilypondSettingNameManager.


DESCRIPTION:: LilyPond setting name manager.


USAGE::

'''
code::
a = FoscNote(60, 1/4);
set(a).postln;
'''
------------------------------------------------------------------------------------------------------------ */
FoscLilypondSettingNameManager : FoscLilypondNameManager {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • at (abjad: __getattr__)
    -------------------------------------------------------------------------------------------------------- */
    at { |name|
        var camelName, typeName, context;
        camelName = name.asString.toUpperCamelCase;
        case
        { name[0] == $_ } {
            try {
                ^vars[name.asSymbol];
            } {
                typeName = this.name.asString;
                throw("% has not attribute: %".format(typeName, name));
            };
        }
        // !!!TODO: contexts import from ly files not yet implemented
        // { contexts.includes(camelName) } {
        //     try {
        //         ^vars[("_" ++ name).asSymbol];
        //     } {
        //         context = FoscLilypondManager();
        //         vars[("_" ++ name).asSymbol] = context;
        //         ^context;
        //     };
        // }
        {
            try {
                ^vars[name.asSymbol];
            } {
                typeName = this.name.asString;
                throw("% has not attribute: %".format(typeName, name));
            };
        };
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • prAttributeTuples
    -------------------------------------------------------------------------------------------------------- */
    prAttributeTuples {
        var result, prefixedContextName, lilypondType, contextProxy, pairs, triple;
        var attributeName, attributeValue;
        result = [];
        vars.keysValuesDo { |name, val|
            if (val.isMemberOf(FoscLilypondNameManager)) {
                prefixedContextName = name;
                lilypondType = prefixedContextName.asString.strip("_");
                contextProxy = val;
                pairs = contextProxy.prGetAttributePairs;
                pairs.do { |pair|
                    # attributeName, attributeValue = pair;
                    triple = [lilypondType, attributeName, attributeValue];
                    result = result.add(triple);
                };
            } {
                result = result.add([name, val]);
            };
        };
        ^result;
    }
}
