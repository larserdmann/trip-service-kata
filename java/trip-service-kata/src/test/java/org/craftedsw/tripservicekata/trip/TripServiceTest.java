package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Trip Service")
public class TripServiceTest {

	@Mock
	TripDAO dao;
	@InjectMocks @Spy
	TripService tripService;
	@Mock
	UserSession userSession;

	@Test @DisplayName("User not logged in.")
	void userNotLoggedIn() {

		// given
		when(userSession.getLoggedUser()).thenReturn(null);
		tripService.setSession(userSession);

		// when
		assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(null));
	}

	@Test @DisplayName("Logged in user not friend")
	void loggedInUserNotFriend() {

		User loggedInUser = new User();
		User user = new User();

		// given
		when(userSession.getLoggedUser()).thenReturn(loggedInUser);
		TripService tripService = new TripService(new TripDAO());
		tripService.setSession(userSession);

		// when
		List<Trip> tripsByUser = tripService.getTripsByUser(user);

		// then
		assertTrue(tripsByUser.isEmpty());
	}

	@Test @DisplayName("Logged in user friend")
	void loggedInUserFriend() {

		// given
		User loggedInUser = new User();
		User user = new User();
		user.addFriend(loggedInUser);

		when(dao.findTripsBy(user)).thenReturn(emptyList());
		when(userSession.getLoggedUser()).thenReturn(loggedInUser);
		tripService.setSession(userSession);

		// when
		List<Trip> tripsByUser = tripService.getTripsByUser(user);

		//then
		assertTrue(tripsByUser.isEmpty());
	}

	@Test
	public void loggedInUserWithFriendAndTrips() {
		// given
		User loggedInUser = new User();
		User user = new User();
		user.addFriend(loggedInUser);

		Trip trip = new Trip();
		user.addTrip(trip);

		when(dao.findTripsBy(user)).thenReturn(Arrays.asList(trip));
		when(userSession.getLoggedUser()).thenReturn(loggedInUser);
		tripService.setSession(userSession);

		// when
		List<Trip> tripsByUser = tripService.getTripsByUser(user);

		//then
		assertTrue(tripsByUser.contains(trip));
	}
}
