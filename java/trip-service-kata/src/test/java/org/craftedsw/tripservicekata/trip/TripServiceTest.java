package org.craftedsw.tripservicekata.trip;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Trip Service")
public class TripServiceTest {

	@InjectMocks @Spy
	TripService tripService;

	@Test @DisplayName("User not logged in.")
	void userNotLoggedIn() {

		UserSession userSession = mock(UserSession.class);

		// given
		when(userSession.getLoggedUser()).thenReturn(null);
		TripService tripService = new TripService();
		tripService.setSession(userSession);

		// when
		try {
			List<Trip> tripsByUser = tripService.getTripsByUser(null);
			fail();
		} catch (UserNotLoggedInException e) {

		}

	}

	@Test @DisplayName("Logged in user not friend")
	void loggedInUserNotFriend() {

		User loggedInUser = new User();
		User user = new User();
		UserSession userSession = mock(UserSession.class);

		// given
		when(userSession.getLoggedUser()).thenReturn(loggedInUser);
		TripService tripService = new TripService();
		tripService.setSession(userSession);

		// when
		List<Trip> tripsByUser = tripService.getTripsByUser(user);

		// then
		assertTrue(tripsByUser.isEmpty());
	}

	@Test @DisplayName("Logged in user friend")
	void loggedInUserFriend() {

		// given
		tripService = spy(TripService.class);

		User loggedInUser = new User();
		User user = new User();
		user.addFriend(loggedInUser);

		UserSession userSession = mock(UserSession.class);

		doReturn(emptyList()).when(tripService).findTripsByUser(any());
		when(userSession.getLoggedUser()).thenReturn(loggedInUser);
		tripService.setSession(userSession);

		// when
		List<Trip> tripsByUser = tripService.getTripsByUser(user);

		//then
		assertTrue(tripsByUser.isEmpty());
	}
}
