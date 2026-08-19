<template>
    <v-row>
      <v-col>
        <h2>News Timeline</h2>
        <v-divider></v-divider>
      </v-col>
    </v-row>

    <v-timeline side="end" layout="dense" class="news-timeline">
      <v-timeline-item
        v-for="(item, index) in timelineItems"
        :key="index"
        :dot-color="item.color"
        size="small"
        class="mb-6"
      >
        <template v-slot:opposite>
          <div class="text-caption font-weight-bold text-md-body2">
            <div class="text-primary font-weight-bold">{{ item.date }}</div>
            <div class="text-grey">{{ item.time }}</div>
          </div>
        </template>

        <v-card 
          class="news-card"
          :class="`border-l-4 border-${item.color}`"
          elevation="2"
        >
          <v-card-title class="pb-2">
            <v-row no-gutters align="center" class="gap-2">
              <v-icon :color="item.color" size="small">{{ item.icon }}</v-icon>
              <span class="text-h6">{{ item.title }}</span>
            </v-row>
          </v-card-title>

          <v-card-text class="text-body2 line-height-lg">
            {{ item.content }}
          </v-card-text>

          <v-card-actions v-if="item.tags" class="pt-0">
            <v-chip
              v-for="tag in item.tags"
              :key="tag"
              size="small"
              variant="outlined"
              :color="item.color"
              class="mr-2"
            >
              {{ tag }}
            </v-chip>
          </v-card-actions>
        </v-card>
      </v-timeline-item>
    </v-timeline>
</template>

<script setup>
import { ref } from 'vue'

const timelineItems = ref([
  {
    date: "21.11.2020",
    time: "12:15",
    title: "US Election Updates",
    icon: "mdi-vote",
    color: "blue",
    content: "US President Trump's attempts to contest the election results in states where his challenger Joe Biden won remains unsuccessful. Recounts in the US state of Georgia, among others, conclude that Biden was able to secure the majority of ballots there. Elsewhere, lawsuits from Trump's campaign led nowhere. The incumbent US president continues to claim that the election was rigged and that he actually won. Meanwhile, several Republicans are also calling for the transition phase to be initiated in order to clear the way for US President-elect Joe Biden to take office.",
    tags: ["Politics", "USA", "Election"]
  },
  {
    date: "21.11.2020",
    time: "12:00",
    title: "Leipzig Demonstration Alert",
    icon: "mdi-alert-circle",
    color: "orange",
    content: "In Leipzig (Germany), the police are preparing for another large-scale demonstration by Corona skeptics. In the run-up to the so-called 'Querdenken' demonstration, both right-wing extremists as well as left-wing extremists mobilized at counter-demonstrations. Both a stronger police presence and metal grilles and identity checks are intended to prevent attacks on press representatives and prevent riots, as well as non-compliance with the Corona rules. The police are expecting several 1000 participants.",
    tags: ["Germany", "Security", "Protest"]
  },
  {
    date: "21.11.2020",
    time: "11:30",
    title: "Corona Infection Report",
    icon: "mdi-virus",
    color: "red",
    content: "New corona infections in Germany are still at a very high level. The Robert Koch Institute reported 22,964 new infections within 24 hours, according to all health authorities.",
    tags: ["Health", "COVID-19", "Germany"]
  },
  {
    date: "20.11.2020",
    time: "14:30",
    title: "Bundestag Security Incident",
    icon: "mdi-shield-alert",
    color: "purple",
    content: "After right-wing media activists who were allegedly infiltrated into the German Bundestag by 3 AFD deputies on Wednesday and have deliberately harassed members of parliament and in some cases invaded the offices of the members of parliament without authority, the Bundestag debated in a current hour about this incident. The debate in the plenary hall showed fierce accusations against the AFD with various accusations that they are acting undemocratically and ridiculing Parliament. The AFD expressed apologies with the speech of the group chairman Alexander Gauland and rejected the allegations.",
    tags: ["Politics", "Germany", "Security"]
  },
  {
    date: "20.11.2020",
    time: "07:30",
    title: "Record Corona Cases",
    icon: "mdi-chart-line",
    color: "red",
    content: "According to the Robert Koch Institute, the number of new corona infections in Germany has reached a new high. According to the information of all health authorities, 23,648 new infections were reported to the RKI within 24 hours throughout Germany.",
    tags: ["Health", "COVID-19", "Statistics"]
  }
])
</script>

<style scoped>
.news-card 
{
  transition: all 0.3s ease;
  border-radius: 8px;
  border-left: 4px solid;
}

.news-card:hover 
{
  transform: translateY(-2px);
}

.line-height-lg 
{
  line-height: 1.6;
  letter-spacing: 0.3px;
}

:deep(.v-timeline-item__dot) 
{
  box-shadow: 0 0 0 4px rgba(255, 255, 255, 0.8);
}

@media (max-width: 960px) 
{
  :deep(.v-timeline) {
    padding-left: 0;
  }
}
</style>