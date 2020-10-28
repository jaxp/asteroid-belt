import { AuthService } from '@/app/auth/auth.service';
import { User } from '@/app/shared/model';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.less']
})
export class LayoutComponent implements OnInit {

  isCollapsed = false;
  user: User;

  constructor(private authService: AuthService) { }

  logout(): void {
    this.authService.logout();
  }

  ngOnInit(): void {
    this.authService.getUser()
      .subscribe(user => this.user = user);
    this.authService.getAuthorities();
  }

}
