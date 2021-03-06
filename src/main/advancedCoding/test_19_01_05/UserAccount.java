package main.advancedCoding.test_19_01_05;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;


/*
HOMEWORK!
Change constructors to use UserData. Rerun unit tests - GREEN!!!
 */
public final class UserAccount {

    private String email;
    private String firstName;
    private String secondName;
    private String password;
    private int cashCredits;
    private TreeSet<Subscription> subscriptions;


    public UserAccount(String email,
                       String firstName,
                       String secondName,
                       String password,
                       int cashCredits,
                       TreeSet<Subscription> subscriptions) {

        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.password = password;
        this.cashCredits = cashCredits;
        this.subscriptions = subscriptions;
    }


    public UserAccount(String email,
                       String firstName,
                       String secondName,
                       String password) {
        this(email,
                firstName,
                secondName,
                password,
                0,
                new TreeSet<Subscription>());

    }


    public String getEmail() {
        return email;
    }


    public String getFirstName() {
        return firstName;
    }


    public String getSecondName() {
        return secondName;
    }


    public String getPassword() {
        return password;
    }


    public int getCashCredits() {
        return cashCredits;
    }


    public TreeSet<Subscription> getSubscriptions() {
        return subscriptions;
    }


    @Override
    // If two objects are equal by equals() method
    // then there hashcode returned by hashCode() method must be same.
    public boolean equals(Object other) {

        // common part to all equals implementations
        if (this == other) return true;

        if (other == null) return false;
        if (!(other instanceof UserAccount))return false;
        // end common part

        final UserAccount otherUserAccount = (UserAccount) other;

        return Objects.equals(
                this.email,
                otherUserAccount.email);
    }


    @Override
    public int hashCode() {

        return Objects.hash(this.email);
    }


    @Override
    public String toString() {

        return String.join(", ",
                email,
                firstName,
                secondName,
                cashCredits + "",
                getSubscriptionsText(subscriptions));
    }


    private String getSubscriptionsText(Set<Subscription> subscriptions) {

        String subscriptionsText = "[";

/*        for(Subscription subscription : subscriptions){
            subscriptionsText += subscription.getSubscriptionName() + ", ";
        }

        subscriptionsText = subscriptionsText.substring(
                0,
                subscriptionsText.length()-2);*/

        subscriptionsText += subscriptions.stream()
                //.map(subscription -> subscription.getSubscriptionName())
                .map(Subscription::getSubscriptionName)
                .collect(Collectors.joining(", "));

        subscriptionsText += "]";

        return subscriptionsText;
    }

}

