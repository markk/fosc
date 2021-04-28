/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscDateTimeToken


SUMMARY:: Returns a FoscDateTimeToken.


DESCRIPTION:: A LilyPond file date / time token.


USAGE::

'''
'''
>>> lilypondfiletools.DateTimeToken()
DateTimeToken()
------------------------------------------------------------------------------------------------------------ */
FoscDateTimeToken : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    •
    '''
    '''
    __slots__ = (
            '_date_string',
            )


    def __init__(self, date_string=None):
        assert isinstance(date_string, (str, type(None)))
        self._date_string = date_string
    -------------------------------------------------------------------------------------------------------- */
    var dateString;
    *new { |dateString|
        if ((dateString.isString || { dateString.isNil }).not) {
            throw("%::new: dateString must be string or nil.".format(this.species));
        };
        ^super.new.init(dateString);
    }
    init { |string|
        dateString = string;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • format
    '''
    '''
    def __format__(self, format_specification=''):
            r'''Formats date / time token.

            >>> token = lilypondfiletools.DateTimeToken()
            >>> print(format(token)) # doctest: +SKIP
            2014-01-04 14:42

            Returns string.
            '''
            from abjad.tools import systemtools
            if format_specification in ('', 'lilypond'):
                return self._get_lilypond_format()
            elif format_specification == 'storage':
                return systemtools.StorageFormatAgent(self).get_storage_format()
            return str(self)

    FoscDateTimeToken().format;
    -------------------------------------------------------------------------------------------------------- */
    format {
        ^this.prGetLilypondFormat;
    }
    /* --------------------------------------------------------------------------------------------------------
    • asCompileString
    '''
    '''
    def __repr__(self):
        r'''Gets interpreter representation of date / time token.

        >>> lilypondfiletools.DateTimeToken()
        DateTimeToken()

        Returns string.
        '''
        date_string = self._date_string or ''
        return '{}({})'.format(type(self).__name__, date_string)
    -------------------------------------------------------------------------------------------------------- */
    asCompileString {
        ^this.notYetImplemented(thisMethod);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • prGetLilypondFormat

    def _get_lilypond_format(self):
            return self.date_string

    a = FoscDateTimeToken();
    a.prGetLilypondFormat;
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormat {
        ^this.dateString;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    /* --------------------------------------------------------------------------------------------------------
    • dateString

    Gets date string of date / time token.

    Returns string.

    '''
    code::
    a = FoscDateTimeToken();
    a.dateString.postln;
    '''
    @property
    def date_string(self):
        date_string = self._date_string or time.strftime('%Y-%m-%d %H:%M')
        return date_string

    -------------------------------------------------------------------------------------------------------- */
    dateString {
        dateString = dateString ?? { Date.getDate.format("%Y-%m-%d %H:%M") };
        ^dateString;
    }
}
