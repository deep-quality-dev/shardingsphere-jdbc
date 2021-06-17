package com.test.sharding.utils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

/**
 * @author liran
 */
public class DateTimeUtil {

    public static LocalDateTime dateToLocalDateTime(Date date) {

        Instant instant = date.toInstant();

        ZoneId zone = ZoneId.systemDefault();

        return LocalDateTime.ofInstant(instant, zone);

    }

    public static LocalDate dateToLocalDate(Date date) {

        LocalDateTime localDateTime = dateToLocalDateTime(date);

        return localDateTime.toLocalDate();

    }

    public static LocalTime dateToLocalTime(Date date) {

        LocalDateTime localDateTime = dateToLocalDateTime(date);

        return localDateTime.toLocalTime();

    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {

        ZoneId zone = ZoneId.systemDefault();

        Instant instant = localDateTime.atZone(zone).toInstant();

        return Date.from(instant);

    }

    public static Date localDateToDate(LocalDate localDate) {

        ZoneId zone = ZoneId.systemDefault();

        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();

        return Date.from(instant);

    }

    public static Date localTimeToDate(LocalTime localTime) {

        LocalDate localDate = LocalDate.now();

        ZoneId zone = ZoneId.systemDefault();

        Instant instant = LocalDateTime.of(localDate, localTime).atZone(zone).toInstant();

        return Date.from(instant);

    }


    /**
     * 将一段时间按照固定的时间间隔进行分割
     * @param startDate
     * @param endDate
     * @param interval  间隔分钟
     * @return
     */
    public static List<HashMap<String, Date>> getIntervalTimeList(Date startDate, Date endDate, int interval){
        List<HashMap<String, Date>> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        while(calendar.getTime().getTime()<=endDate.getTime()){
            HashMap<String, Date> hashMap = new HashMap<>();
            hashMap.put("startDate",calendar.getTime());
            calendar.add(Calendar.MINUTE,interval);
            if(calendar.getTime().getTime()>endDate.getTime()){
                hashMap.put("endDate",endDate);
            }else{
                hashMap.put("endDate",calendar.getTime());
            }
            list.add(hashMap);
        }
        return list;
    }
    public static DateBean getDateBeanByTaskWeek(Date dateTime, int interval){
        Calendar c=Calendar.getInstance();
        if(Objects.isNull(dateTime)){
            c.setTime(new Date(System.currentTimeMillis()));
            c.add(Calendar.DATE, -7);
        }else {
            c.setTime(dateTime);
        }
        int year = c.get(Calendar.YEAR);
        Date yearFirst = getYearFirst(year);
        Date yearLast = getYearLast(year);
        c.setTime(yearFirst);
        int sortNo=1;
        while (c.getTime().getTime()<yearLast.getTime()){
            DateBean dateBean = new DateBean();
            if(sortNo<10){
                dateBean.setSortNo(year*10+sortNo);
            }else if(sortNo>10&&sortNo<100){
                dateBean.setSortNo(year*100+sortNo);
            }
            dateBean.setStartDate(c.getTime());
            c.add(Calendar.DATE, 6);
            c.set(Calendar.HOUR,23);
            c.set(Calendar.MINUTE,59);
            c.set(Calendar.SECOND,59);
            if(c.getTime().getTime()>getYearLast(year).getTime()){
                dateBean.setEndDate(getYearLast(year));
            }else {
                dateBean.setEndDate(c.getTime());
            }
            c.add(Calendar.SECOND,1);
            List<HashMap<String, Date>> intervalTimeList = getIntervalTimeList(dateBean.getStartDate(), dateBean.getEndDate(), interval);
            dateBean.setIntervalTime(intervalTimeList);
            sortNo++;
            if(dateBean.getStartDate().getTime()<dateTime.getTime()&&dateTime.getTime()<dateBean.getEndDate().getTime()){
                return dateBean;
            }
        }
        return null;
    }



    public static DateBean getDateBeanByTaskDay(Date dateTime, int interval, SimpleDateFormat simpleDateFormat){
        DateBean dateBean = new DateBean();
        Calendar c=Calendar.getInstance();
        if(Objects.isNull(dateTime)){
            c.add(Calendar.DATE,-1);
        }else {
            c.setTime(dateTime);
        }
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND,0);
        dateBean.setStartDate(c.getTime());;
        c.set(Calendar.HOUR,23);
        c.set(Calendar.MINUTE,59);
        c.set(Calendar.SECOND,59);
        dateBean.setEndDate(c.getTime());
        List<HashMap<String, Date>> intervalTimeList = getIntervalTimeList(dateBean.getStartDate(), dateBean.getEndDate(), interval);
        dateBean.setIntervalTime(intervalTimeList);
        dateBean.setSortNo(Integer.valueOf(simpleDateFormat.format(c.getTime())));
        return dateBean;
    }




    /**
     * 获取某年第一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期 结束始时间
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        calendar.roll(Calendar.HOUR_OF_DAY,-1);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }
    public static class DateBean{
        private Integer sortNo;
        private Date startDate;
        private Date endDate;
        /**
         * 根据startDate 和endDate 切割成固定长度的时间集合
         */
        private List<HashMap<String, Date>> intervalTime;

        public Integer getSortNo() {
            return sortNo;
        }

        public void setSortNo(Integer sortNo) {
            this.sortNo = sortNo;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public List<HashMap<String, Date>> getIntervalTime() {
            return intervalTime;
        }

        public void setIntervalTime(List<HashMap<String, Date>> intervalTime) {
            this.intervalTime = intervalTime;
        }

        @Override
        public String toString() {
            return "DateBean{" +
                    "sortNo=" + sortNo +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", intervalTime=" + intervalTime +
                    '}';
        }
    }



}
