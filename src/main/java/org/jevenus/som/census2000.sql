create table census2000 (
       cntyidfp  varchar(5) PRIMARY KEY,
       name varchar(5),
       total_population varchar(9),

       --
       -- sex and age (24)
       -- 
       male varchar(9),
       famale varchar(9),
       age_under5 varchar(9),
       age_5to9 varchar(9),
       age_10to14 varchar(9),
       age_15to19 varchar(9),
       age_20to24 varchar(9),
       age_25to34 varchar(9),
       age_35to44 varchar(9),
       age_45to54 varchar(9),
       age_55to59 varchar(9),
       age_60to64 varchar(9),
       age_65to74 varchar(9),
       age_75to84 varchar(9),
       age_85andover varchar(9),
       median_age varchar(9),
       _18_andover varchar(9),
       _18_andover_male varchar(9),
       _18_andover_famale varchar(9),
       _21_andover varchar(9),
       _62_andover varchar(9),
       _65_andover varchar(9),
       _65_andover_male varchar(9),
       _65_andover_famale varchar(9),
       
       --
       -- race (33)
       --
       one_race varchar(9),
       r1_white varchar(9),
       r1_black_or_african_american varchar(9),
       r1_american_indian_and_alaska_native varchar(9),
       r1_asian varchar(9),
       r1_asian_indian varchar(9),
       r1_chinese varchar(9),
       r1_filipino varchar(9),
       r1_japanese varchar(9),
       r1_korean varchar(9),
       r1_vietnamese varchar(9),
       r1_other_asian varchar(9),
       r1_native_hawaiian_and_other_pacific_islander varchar(9),
       r1_native_hawaiian varchar(9),
       r1_guamanian_or_chamorro varchar(9),
       r1_samoan varchar(9),
       r1_other_pacific_islander varchar(9),
       r1_some_other_race varchar(9),
       r1_two_or_more_races varchar(9),

       white varchar(9),
       black_or_african_american varchar(9),
       american_indian_and_alaska_native varchar(9),
       asian varchar(9),
       native_hawaiian_and_other_pacific_islander varchar(9),
       some_other_race varchar(9),

       total_population1 varchar(9),
       hispanic_or_latino varchar(9),
       mexican varchar(9),
       puerto_rican varchar(9),
       cuban varchar(9),
       other_hispanic_or_latino varchar(9),
       not_hispanic_or_latino varchar(9),
       white_alon varchar(9),

       --
       -- relationship (13)
       --
       total_population2 varchar(9),
       in_households varchar(9),
       householder varchar(9),
       spouse varchar(9),
       child varchar(9),
       own_child_under18 varchar(9),
       other_relatives varchar(9),
       other_relatives_under18 varchar(9),
       nonrelatives varchar(9),
       unmarried_partner varchar(9),
       in_group_quarters varchar(9),
       institutionalized_population varchar(9),
       noninstitutionalized_population varchar(9),

       --
       -- household by type (14)
       --
       total_population3 varchar(9),
       family_households_families varchar(9),
       with_own_childern_under18 varchar(9),
       married_couple_family varchar(9),
       with_own_child_under18 varchar(9),
       female_householder_no_husband_present varchar(9),
       pre_with_own_children_under18 varchar(9),
       nonfamily_households varchar(9),
       householder_living_alone varchar(9),
       householder_65_years_and_over varchar(9),

       households_with_individuals_under18 varchar(9),
       households_with_individuals_65andover varchar(9),
       average_household_size varchar(9),
       avarage_family_size varchar(9),
       
       --
       -- housing occupancy (6)
       --
       total_housing_units varchar(9),
       occupied_housing_units varchar(9),
       vacant_housing_units varchar(9),
       for_seasonal_recreational_or_occasional_use varchar(9),

       homeowner_vacancy_rate varchar(9),
       rental_vacancy_rate varchar(9),

       --
       -- housing tenure (5)
       --
       occupied_housing_units1 varchar(9),
       owner_occupied_housing_units varchar(9),
       renter_occupide_housing_units varchar(9),
       average_household_size_of_owner_occupied_units varchar(9),
       average_household_size_of_renter_occupied_units varchar(9)
       
);
