package basc.controller;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class AuthenticationTokenTest {

    LocalDateTime expires = LocalDateTime.of(2016, Month.APRIL, 13, 18, 24, 55);

    @Test
    public void accessTokenToString() {
        AuthenticationToken ac = new AuthenticationToken("ASHWLIkouP2O6_bgA2wWReRhletgWKHYjLqDaqb0LFfamim9RjexTo22ujRIP_cjLiRiSyQXyt2kM1eXU2XLFZQ0Hro15HikJQT_eNeT_9XQ", expires);

        assertThat(ac.toString(), is(equalTo("ASHWLIkouP2O6_bgA2wWReRhletgWKHYjLqDaqb0LFfamim9RjexTo22ujRIP_cjLiRiSyQXyt2kM1eXU2XLFZQ0Hro15HikJQT_eNeT_9XQ")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyTokenIsNotValid() {
        new AuthenticationToken("", expires);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullTokenIsNotValid() {
        new AuthenticationToken(null, expires);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tokenMustStartWithCharacter_A_() {
        new AuthenticationToken("BSHWLIkouP2O6_bgA2wWReRhletgWKHYjLqDaqb0LFfamim9RjexTo22ujRIP_cjLiRiSyQXyt2kM1eXU2XLFZQ0Hro15HikJQT_eNeT_9XQ", expires);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tokenMustHaveLength108() {
        new AuthenticationToken("AhasNotLength108", expires);
    }

    @Test
    public void tokenIsValid10MinutesBeforeExpiration() {
        AuthenticationToken ac = new AuthenticationToken("ASHWLIkouP2O6_bgA2wWReRhletgWKHYjLqDaqb0LFfamim9RjexTo22ujRIP_cjLiRiSyQXyt2kM1eXU2XLFZQ0Hro15HikJQT_eNeT_9XQ", expires);

        assertThat(ac.isValidAt(expires.minusMinutes(10)), is(true));
    }

    @Test
    public void tokenIsValidExactlyAtExpiration() {
        AuthenticationToken ac = new AuthenticationToken("ASHWLIkouP2O6_bgA2wWReRhletgWKHYjLqDaqb0LFfamim9RjexTo22ujRIP_cjLiRiSyQXyt2kM1eXU2XLFZQ0Hro15HikJQT_eNeT_9XQ", expires);

        assertThat(ac.isValidAt(expires), is(true));
    }

    @Test
    public void tokenIsNotValid10MinutesAfterExpiration() {
        AuthenticationToken ac = new AuthenticationToken("ASHWLIkouP2O6_bgA2wWReRhletgWKHYjLqDaqb0LFfamim9RjexTo22ujRIP_cjLiRiSyQXyt2kM1eXU2XLFZQ0Hro15HikJQT_eNeT_9XQ", expires);

        assertThat(ac.isValidAt(expires.plusMinutes(10)), is(false));
    }

}
