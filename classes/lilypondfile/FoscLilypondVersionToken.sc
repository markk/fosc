/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscLilypondVersionToken


SUMMARY:: Returns a FoscLilypondVersionToken.


DESCRIPTION:: A LilyPond file ``\version`` token.


USAGE::

'''
code::
FoscLilypondVersionToken('2.19.0').format;

code::
FoscLilypondVersionToken().format;
'''
------------------------------------------------------------------------------------------------------------ */
FoscLilypondVersionToken : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <versionString;
    *new { |versionString|
        if (versionString.isKindOf(FoscLilypondVersionToken)) {
            versionString = versionString.versionString;
        };
        ^super.new.init(versionString);
    }
    init { |argVersionString|
        if (argVersionString.isNil) { argVersionString = FoscConfiguration.getLilypondVersionString };
        versionString = argVersionString;

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • format

    Formats LilyPond version token.

    Return string.

    '''
    code::
    FoscLilypondVersionToken('2.19.0').format;

    code::
    FoscLilypondVersionToken().format;
    '''
    def __format__(self, format_specification=''):
        from abjad.tools import systemtools
        if format_specification in ('', 'lilypond'):
            return self._get_lilypond_format()
        elif format_specification == 'storage':
            return systemtools.StorageFormatAgent(self).get_storage_format()
        return str(self)
    -------------------------------------------------------------------------------------------------------- */
    format {
        ^this.prGetLilypondFormat;
    }
    /* --------------------------------------------------------------------------------------------------------
    • asCompileString (abjad: __repr__)

    Gets interpreter representation of LilyPond version_string token.

    Returns string.
    '''
    '''
    def __repr__(self):
        return '{}({!r})'.format(type(self).__name__, self.version_string)
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • prGetLilypondFormat

    def _get_lilypond_format(self):
            return r'\version "{}"'.format(self.version_string)
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormat {
        ^"\\version %".format(versionString.asString.quote);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • versionString

    Gets version string of LilyPond version token.

    Returns string.

    '''
    code::
    a = FoscLilypondVersionToken();
    a.versionString.postln;
    '''
    @property
    def version_string(self):
        return self._version_string

    '''
    Gets version string from install environment:

    code::
    a = FoscLilypondVersionToken();
    a.versionString.postln;
    '''
    '''
    Gets version string from explicit input:

    code::
    a = FoscLilypondVersionToken("2.19.0");
    a.versionString.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
}
