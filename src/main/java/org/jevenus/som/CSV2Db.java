// 	$Id: CSV2Db.java,v 1.3 2012/02/12 12:10:45 arch Exp arch $	

package org.jevenus.som;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CSV2Db
{
  public static void main(String[] paramArrayOfString)
  {
    String str1 = null;
    String str2 = null;
    int i = 0;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    String str3 = "insert into census2000(cntyidfp, name, total_population, male," + 
                  "famale,age_under5, age_5to9, age_10to14, age_15to19, age_20to24," +
                  "age_25to34,age_35to44, age_45to54, age_55to59, age_60to64, age_65to74," +
                  "age_75to84,age_85andover, median_age, _18_andover, _18_andover_male," +
                  "_18_andover_famale,_21_andover, _62_andover, _65_andover, _65_andover_male," +
                  "_65_andover_famale,one_race, r1_white," +
                  "r1_black_or_african_american,r1_american_indian_and_alaska_native, r1_asian," +
                  "r1_asian_indian, r1_chinese,r1_filipino, r1_japanese, r1_korean, r1_vietnamese," +
                  "r1_other_asian,r1_native_hawaiian_and_other_pacific_islander," +
                  "r1_native_hawaiian,r1_guamanian_or_chamorro, r1_samoan," +
                  "r1_other_pacific_islander,r1_some_other_race, r1_two_or_more_races, white," +
                  "black_or_african_american,american_indian_and_alaska_native," +
                  "asian,native_hawaiian_and_other_pacific_islander, some_other_race," +
                  "total_population1,hispanic_or_latino, mexican, puerto_rican, cuban," +
                  "other_hispanic_or_latino,not_hispanic_or_latino, white_alon, total_population2," +
                  "in_households,householder, spouse, child, own_child_under18," +
                  "other_relatives,other_relatives_under18, nonrelatives, unmarried_partner," +
                  "in_group_quarters,institutionalized_population," +
                  "noninstitutionalized_population,total_population3, family_households_families," +
                  "with_own_childern_under18,married_couple_family," +
                  "with_own_child_under18,female_householder_no_husband_present," +
                  "pre_with_own_children_under18,nonfamily_households, householder_living_alone," +
                  "householder_65_years_and_over,households_with_individuals_under18," +
                  "households_with_individuals_65andover,average_household_size," +
                  "avarage_family_size, total_housing_units,occupied_housing_units," +
                  "vacant_housing_units,for_seasonal_recreational_or_occasional_use," +
                  "homeowner_vacancy_rate,rental_vacancy_rate, occupied_housing_units1," +
                  "owner_occupied_housing_units,renter_occupide_housing_units," +
                  "average_household_size_of_owner_occupied_units,average_household_size_of_renter_occupied_units)" +
                  "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    try
    {
      BufferedReader localBufferedReader = new BufferedReader(new FileReader("/tmp/middle.txt"));
      localConnection = ConnectPostsql.getConn();
      localPreparedStatement = localConnection.prepareStatement(str3);
      if ((str1 = localBufferedReader.readLine()) != null) {
        String[] arrayOfString1 = str1.split(",");
        i = 0;
        int j = 0;
        for (String str4 : arrayOfString1) {
          i++;
          if ((i >= 1) && (i <= 3))
          {
            continue;
          }
          if (i == 4) {
            str2 = str4.substring(1, 3);
          } else {
            if (i == 5) {
              str4 = str2 + str4.substring(1, 4);
            }

            if (i == 15) {
              str4 = str4.substring(1);
            }

            if ((i >= 6) && (i <= 14))
            {
              continue;
            }
            if (i == 16)
            {
              continue;
            }
            j++; localPreparedStatement.setString(j, str4);
          }
        }
        System.out.println(j);
      }

      localPreparedStatement.close();
      localBufferedReader.close();
    } catch (IOException localIOException) {
      System.err.println("file input error");
    } catch (SQLException localSQLException) {
      System.err.println("sql error");
    }
  }
}
