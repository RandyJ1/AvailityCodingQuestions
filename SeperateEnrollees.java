import java.util.*;
import java.io.BufferedReader;  
import java.io.FileReader;  
import java.io.IOException;  
import java.io.PrintWriter;
import java.io.File;

/* Coding exercise:  Availity receives enrollment files from various benefits management and enrollment solutions 
   (I.e. HR platforms, payroll platforms).  Most of these files are typically in EDI format.  
   However, there are some files in CSV format.  For the files in CSV format, write a program in a language that makes 
   sense to you that will read the content of the file and separate enrollees by insurance company in its own file. 
   Additionally, sort the contents of each file by last and first name (ascending).  
   Lastly, if there are duplicate User Ids for the same Insurance Company, then only the record with the highest 
   version should be included. The following data points are included in the file: 

    User Id (string) 
    First Name (string)  
    Last Name (string) 
    Version (integer) 
    Insurance Company (string) 

    @Author: Randy Jaouhari
*/
public class SeperateEnrollees {

    public static void main(String[] args) {
        HashMap<String, ArrayList<User>> insuranceCompanyMapper = new HashMap<>();
        String line = "";  
        String comma = ",";  
        // read from file and seperate each insurance company and it's users into a key/value mapping.
        String firstLine = "";
        try  {  
            BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0]));  
            firstLine = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null)  {  
                String[] userInfo = line.split(comma);    // use comma as separator  
                User user = new User(userInfo[0], userInfo[1], userInfo[2], Integer.parseInt(userInfo[3]));
                if(insuranceCompanyMapper.containsKey(userInfo[4])) {
                    // we'll insert the new user in a sorted pattern, this way we don't have sort them later on.
                    int idxToAdd = insertionSort(insuranceCompanyMapper.get(userInfo[4]), userInfo[2] + " " + userInfo[1]);
                    if(idxToAdd < insuranceCompanyMapper.get(userInfo[4]).size()) {
                        insuranceCompanyMapper.get(userInfo[4]).add(idxToAdd, user);
                    } else {
                        insuranceCompanyMapper.get(userInfo[4]).add(user);
                    }
                } else {
                    // If the insurance company hasn't been seen before make a new key for them.
                    ArrayList<User> userList = new ArrayList<>();
                    userList.add(user);
                    insuranceCompanyMapper.put(userInfo[4], userList);
                }
            }  
        } catch (IOException e) {  
            e.printStackTrace();    
        } 
        // remove duplicates
        File csvOutputFile = new File(args[0]);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) { 
            for(String k : insuranceCompanyMapper.keySet()) {
                // Get the correct list first.
                insuranceCompanyMapper.put(k, removeDuplicates(insuranceCompanyMapper.get(k)));
                // Print out info for each key.
                pw.println(k);
                insuranceCompanyMapper.get(k)
                    .stream()
                    .map(user -> {
                        String finalString = user.getUserId() + comma + user.getFirstName() + comma + user.getLastName() + comma + user.getVersion();
                        return finalString;
                    })
                    .forEach(pw::println);
                pw.println("");
            } 
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Param users - List of users containing potential duplicates to be removed.
     * @return ret - List of users with removed duplicates only keeping the one with the highest version.
     */
    public static ArrayList<User> removeDuplicates(ArrayList<User> users) {
        ArrayList<User> ret = new ArrayList<>();
        for(int i = 0; i < users.size(); i++) {
            User currentUser = users.get(i);
            int j = i + 1;
            while(j < users.size() && currentUser.equalWithoutVersion(users.get(j))) {
                User secondUser = users.get(j);
                if(!currentUser.higherVersion(secondUser)) {
                    currentUser = secondUser;
                }
                j++;
            }
            ret.add(currentUser);
            i = j - 1;
        }
        return ret;
    }

    /**
     * @param names - List of Users to add to.
     * @param nameToAdd - name to add to the list given, but in the correct spot to maintain sorting.
     * 
     * @return idx - index to add the given name to, as to maintain sorting.
     */
    public static int insertionSort(ArrayList<User> names, String nameToAdd) {
        
        int idx = 0;
        while(idx < names.size() && nameToAdd.compareTo(names.get(idx).getLastName() + " " + names.get(idx).getFirstName()) > 0) {
            idx++;
        }
        return idx;
    }

    private static class User {
        
        private String userId;
        private String firstName;
        private String lastName;
        private int version;

        public User(String userId, String firstName, String lastName, int version) {
            this.userId = userId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.version = version;        
        }

        /**
         * @param user - other user to check with.
         * 
         * @return returns whether or not this user is equal to passed in user in all fields except version.
         */
        public boolean equalWithoutVersion(User user) {
            return this.firstName.equals(user.getFirstName()) &&
                   this.lastName.equals(user.getLastName()) &&
                   this.userId.equals(user.getUserId());
        }

        /**
         * @param user - other user to check with.
         * 
         * @return returns whether or not this user has a higher version than passed in user.
         */
        public boolean higherVersion(User user) {
            return this.version > user.getVersion();
        }

        public String getUserId() {
            return this.userId;
        }

        public String getFirstName() {
            return this.firstName;
        }

        public String getLastName() {
            return this.lastName;
        }

        public int getVersion() {
            return this.version;
        }
    }
}